#!/bin/bash
set -euo pipefail

# Taken from this blog post: https://engineering.block.xyz/blog/adopting-ktfmt-and-detekt
# Specifically this link: https://gist.github.com/vRallev/e9c3c59bba95521f98ffbb487ec44427

KTFMT_VERSION="0.60"

show_help() {
  cat << EOF
Usage: scripts/ktfmt.sh [OPTIONS] [DIRECTORY|FILE...]

Format Kotlin files in the codebase using ktfmt with the Google style --google-style
without invoking Gradle.

This script chunks the operation to avoid "argument list too long" errors when
formatting the entire codebase.

Only files within */src/* directories are formatted, e.g. */src-gen/* is explicitly excluded.

OPTIONS:
  -h, --help              Show this help message and exit
  --dry-run               Dry-run mode: only print files that need formatting
  --set-exit-if-changed   Exit early if any file needs to be formatted
  --verbose               Print all ktfmt output (suppressed by default)
  --plain                 Print only the number of autoformatted files
  --all                   Format entire codebase (required if no directory/file specified)
  --touched               Format only files changed since the parent branch (includes working tree changes)

ARGUMENTS:
  DIRECTORY               Optional: Format only files in this directory (e.g., app/src/main/kotlin)
  FILE...                 Optional: Format one or more .kt files

EXAMPLES:
  Format entire codebase:
    scripts/ktfmt.sh --all

  Format only touched files (changed since parent branch like 'master'):
    scripts/ktfmt.sh --touched

  Dry-run to see which files need formatting:
    scripts/ktfmt.sh --dry-run --all

  Format specific directory:
    scripts/ktfmt.sh app/src/main/kotlin

  Format a single file:
    scripts/ktfmt.sh path/to/File.kt

  Format multiple files:
    scripts/ktfmt.sh path/to/File1.kt path/to/File2.kt path/to/File3.kt

  Format with verbose output:
    scripts/ktfmt.sh --verbose lib/src

  Format with plain output (just show count):
    scripts/ktfmt.sh --plain --all

  Check if any files need formatting and exit:
    scripts/ktfmt.sh --dry-run --set-exit-if-changed --all

  Dry-run on multiple files:
    scripts/ktfmt.sh --dry-run file1.kt file2.kt

  Dry-run on touched files:
    scripts/ktfmt.sh --dry-run --touched

EOF
  exit 0
}

# Logging functions
log() {
  if [ "$verbose" = true ]; then
    echo "$@"
  fi
}

error() {
  echo "$@" >&2
}

# Counter for formatted files (used with --plain)
formatted_count=0

# Function to run ktfmt with proper verbose handling
run_ktfmt() {
  local files_to_format=("$@")
  local ktfmt_args=()
  if [ "$dry_run" = true ]; then
    ktfmt_args+=("--dry-run")
  fi
  if [ "$set_exit_if_changed" = true ]; then
    ktfmt_args+=("--set-exit-if-changed")
  fi

  # The @+ syntax is for bash parameter expansion
  # * Expands to the array elements when the array has elements
  # * Expands to nothing not even an empty string when the array is empty
  if [ "$verbose" = true ] || [ "$dry_run" = true ]; then
    java -jar "$KTFMT_JAR" "${ktfmt_args[@]+"${ktfmt_args[@]}"}" --google-style "${files_to_format[@]}"
  elif [ "$plain" = true ]; then
    # Capture output and count "Done formatting" lines
    local output
    output=$(java -jar "$KTFMT_JAR" "${ktfmt_args[@]+"${ktfmt_args[@]}"}" --google-style "${files_to_format[@]}" 2>&1)
    local count
    count=$(echo "$output" | grep -c "Done formatting" || true)
    formatted_count=$((formatted_count + count))
  else
    java -jar "$KTFMT_JAR" "${ktfmt_args[@]+"${ktfmt_args[@]}"}" --google-style "${files_to_format[@]}" > /dev/null
  fi

  return $?
}

# Function to get touched Kotlin files using git (outputs one file per line)
get_touched_kotlin_files_as_lines() {
  local parent_branches=("master" "origin/master" "main" "origin/main")
  local parent_ref=""

  local reachable_branches=()

  # Record all branches that exist locally or remotely
  for branch in "${parent_branches[@]}"; do
    if git rev-parse --verify -q "$branch" > /dev/null 2>&1; then
      reachable_branches+=("$branch")
    fi
  done

  if [ ${#reachable_branches[@]} -eq 0 ]; then
    error "Error: Could not find a parent branch. Tried: ${parent_branches[*]}"
    exit 1
  fi

  # Compute the nearest parent by finding a merge-base across all reachable refs
  parent_ref=$(git merge-base HEAD "${reachable_branches[@]}" 2>/dev/null || echo "")

  if [ -z "$parent_ref" ]; then
    error "Error: Could not determine merge-base using: ${reachable_branches[*]}"
    exit 1
  fi

  log "Using parent ref: $parent_ref"

  # Get committed changes
  local committed_files
  committed_files=$(git diff --name-only "$parent_ref...HEAD" -- "*.kt" "*.kts" 2>/dev/null || echo "")

  # Get working tree changes (staged and unstaged)
  local working_tree_files
  working_tree_files=$(git status --porcelain=v2 --untracked-files=all 2>/dev/null | while IFS= read -r line; do
    if [[ "$line" =~ ^1\  ]]; then
      # Ordinary changed entry - path is the last field
      echo "$line" | awk '{print $NF}'
    elif [[ "$line" =~ ^2\  ]]; then
      # Renamed/copied entry - extract new path (before tab)
      echo "$line" | awk '{print $NF}' | cut -d$'\t' -f1
    elif [[ "$line" =~ ^\?\  ]]; then
      # Untracked entry - path follows "? "
      echo "${line:2}"
    fi
  done | grep -E '\.(kt|kts)$' || echo "")

  # Combine and deduplicate files
  local all_files
  all_files=$(echo -e "$committed_files\n$working_tree_files" | grep -v '^$' | sort -u)

  # Filter to only include files in src directories (excluding src-gen) and output one per line
  while IFS= read -r file; do
    if [ -f "$file" ] && [[ "$file" == */src/* ]] && [[ "$file" != */src-gen/* ]]; then
      echo "$file"
    fi
  done <<< "$all_files"
}

# Parse arguments
verbose=false
plain=false
format_all=false
format_touched=false
dry_run=false
set_exit_if_changed=false
target_paths=()

while [ $# -gt 0 ]; do
  arg="$1"

  case "$arg" in
    -h|--help)
      show_help
      ;;
    --verbose)
      verbose=true
      shift
      ;;
    --plain)
      plain=true
      shift
      ;;
    --dry-run)
      dry_run=true
      shift
      ;;
    --set-exit-if-changed)
      set_exit_if_changed=true
      shift
      ;;
    --all)
      format_all=true
      shift
      ;;
    --touched)
      format_touched=true
      shift
      ;;
    -*)
      error "Error: Unknown option: $arg"
      error "Run 'scripts/ktfmt.sh --help' for usage information."
      exit 1
      ;;
    *)
      # Non-flag arguments are target paths (directory or files)
      target_paths+=("$arg")
      shift
      ;;
  esac
done

# Show help if no arguments provided
if [ "$verbose" = false ] && [ "$plain" = false ] && [ "$format_all" = false ] && [ "$format_touched" = false ] && [ ${#target_paths[@]} -eq 0 ] && [ "$dry_run" = false ] && [ "$set_exit_if_changed" = false ]; then
  show_help
fi

# Validate mutually exclusive options
if [ "$verbose" = true ] && [ "$plain" = true ]; then
  error "Error: Cannot specify both --verbose and --plain."
  exit 1
fi

if [ "$format_all" = true ] && [ "$format_touched" = true ]; then
  error "Error: Cannot specify both --all and --touched."
  exit 1
fi

if [ "$format_all" = true ] && [ ${#target_paths[@]} -gt 0 ]; then
  error "Error: Cannot specify both --all and a directory/file path."
  exit 1
fi

if [ "$format_touched" = true ] && [ ${#target_paths[@]} -gt 0 ]; then
  error "Error: Cannot specify both --touched and a directory/file path."
  exit 1
fi

# Validate that either --all, --touched, or paths are provided
if [ "$format_all" = false ] && [ "$format_touched" = false ] && [ ${#target_paths[@]} -eq 0 ]; then
  error "Error: Either specify --all flag, --touched flag, or provide a directory/file path."
  exit 1
fi

log "Using ktfmt version: $KTFMT_VERSION"

# Download ktfmt jar if it doesn't exist
KTFMT_JAR="bin/ktfmt-${KTFMT_VERSION}-with-dependencies.jar"
if [ ! -f "$KTFMT_JAR" ]; then
  log "Downloading ktfmt jar..."
  mkdir -p bin
  DOWNLOAD_URL="https://github.com/facebook/ktfmt/releases/download/v${KTFMT_VERSION}/ktfmt-${KTFMT_VERSION}-with-dependencies.jar"

  if command -v curl &> /dev/null; then
    curl -L -o "$KTFMT_JAR" "$DOWNLOAD_URL"
  elif command -v wget &> /dev/null; then
    wget -O "$KTFMT_JAR" "$DOWNLOAD_URL"
  else
    error "Error: Neither curl nor wget is available. Please install one of them."
    exit 1
  fi

  if [ ! -f "$KTFMT_JAR" ]; then
    error "Error: Failed to download ktfmt jar"
    exit 1
  fi

  log "Downloaded ktfmt jar to $KTFMT_JAR"
else
  log "Using existing ktfmt jar: $KTFMT_JAR"
fi

search_dir="."
is_directory=false
has_files=false

if [ "$format_touched" = true ]; then
  log "Finding touched Kotlin files..."

  # Store the function output directly into an array (compatible with Bash 3.2+)
  files=()
  while IFS= read -r line; do
    files+=("$line")
  done < <(get_touched_kotlin_files_as_lines)

  if [ ${#files[@]} -eq 0 ]; then
    log "No touched Kotlin files found."
    exit 0
  fi

  log "Found ${#files[@]} touched Kotlin file(s)."

  if [ "$verbose" = true ]; then
    log "Files to format:"
    for file in "${files[@]}"; do
      log "  $file"
    done
  fi
elif [ ${#target_paths[@]} -gt 0 ]; then
  for target_path in "${target_paths[@]}"; do
    if [ -d "$target_path" ]; then
      if [ $has_files = true ]; then
        error "Error: Cannot mix directories and files as target paths"
        exit 1
      fi
      is_directory=true
      search_dir="$target_path"
    elif [ -f "$target_path" ] && [[ "$target_path" == *.kt ]]; then
      has_files=true
    else
      error "Error: Invalid target path: $target_path"
      exit 1
    fi
  done
fi

if [ $format_all = true ]; then
  search_dir="."
  is_directory=true
fi

if [ $is_directory = true ]; then
  # Find all .kt files in src directories (excluding src-gen)
  # Only search within **/src/** directories to avoid formatting generated files in src-gen
  files=($(find "$search_dir" -type d -name "src-gen" -prune -o -type f -path "*/src/*" -name "*.kt" -print))

  # Check if any .kt files were found
  if [ ${#files[@]} -eq 0 ]; then
    error "No .kt files found."
    exit 1
  fi
elif [ "$format_touched" = true ]; then
  # Files array is already set by get_touched_kotlin_files
  if [ ${#files[@]} -eq 0 ]; then
    log "No touched Kotlin files to format."
    exit 0
  fi
fi

# Process files based on how they were selected
if [ $is_directory = true ] || [ "$format_touched" = true ]; then
  # Calculate the number of chunks needed (max 8,000 files per chunk)
  max_files_per_chunk=8000
  total_files=${#files[@]}
  chunks=$(( (total_files + max_files_per_chunk - 1) / max_files_per_chunk ))

  # Ensure at least 1 chunk
  if [ $chunks -lt 1 ]; then
    chunks=1
  fi

  log "Found $total_files Kotlin files. Processing in $chunks chunk(s)..."

  # Calculate the number of files per chunk
  files_per_chunk=$(( (total_files + chunks - 1) / chunks ))

  # Process files in chunks, running ktfmt exactly 'chunks' times.
  #
  # Processing all chunks in parallel in multiple processes isn't significantly faster, so we avoid
  # this for now.
  for ((i=0; i<${#files[@]}; i+=files_per_chunk)); do
    # Get the current chunk of files
    chunk=("${files[@]:i:files_per_chunk}")

    # Skip empty chunks (in case there are fewer than 'chunks' chunks)
    if [ ${#chunk[@]} -eq 0 ]; then
      continue
    fi

    # Run ktfmt on the current chunk
    log "Running ktfmt on chunk $((i/files_per_chunk + 1))/$chunks"

    run_ktfmt "${chunk[@]}"

    # Exit if ktfmt fails
    if [ $? -ne 0 ]; then
      error "ktfmt failed on chunk $((i/files_per_chunk + 1))."
      exit 1
    fi
  done

  log "Completed running ktfmt on all chunks."
else
  # Format specific files provided as arguments
  log "Formatting files from parameter"
  if [ ${#target_paths[@]} -eq 0 ]; then
    error "Error: No files to format."
    exit 1
  fi

  run_ktfmt "${target_paths[@]}"

  if [ $? -ne 0 ]; then
    error "ktfmt failed."
    exit 1
  fi

  log "Completed running ktfmt."
fi

# Print formatted count in plain mode
if [ "$plain" = true ]; then
  echo "Autoformatted $formatted_count files"
fi
