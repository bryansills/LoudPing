# `:deep-history-runner-jvm`

## Module dependency graph

<!--region graph-->
```mermaid
---
config:
  layout: elk
  elk:
    nodePlacementStrategy: NETWORK_SIMPLEX
---
graph TB
  subgraph :core
    direction TB
    :core:model[model]:::jvm-library
  end
  subgraph :database
    direction TB
    :database:core[core]:::unknown
    :database:jvm[jvm]:::jvm-library
  end
  :track-repo[track-repo]:::jvm-library
  :time[time]:::jvm-library
  :storage[storage]:::jvm-library
  :sneak-network[sneak-network]:::jvm-library
  :sneak[sneak]:::jvm-library
  :session[session]:::jvm-library
  :network-auth[network-auth]:::jvm-library
  :network[network]:::jvm-library
  :deep-history-runner-jvm[deep-history-runner-jvm]:::jvm-library
  :deep-history-dash[deep-history-dash]:::unknown
  :deep-history[deep-history]:::jvm-library
  :coroutines-ext[coroutines-ext]:::jvm-library

  :database:jvm -.-> :database:core
  :deep-history --> :core:model
  :deep-history --> :database:core
  :deep-history --> :track-repo
  :deep-history-dash -.-> :database:core
  :deep-history-dash -.-> :database:jvm
  :deep-history-dash -.-> :deep-history
  :deep-history-dash -.-> :network
  :deep-history-dash -.-> :network-auth
  :deep-history-dash -.-> :session
  :deep-history-dash -.-> :sneak
  :deep-history-dash -.-> :sneak-network
  :deep-history-dash -.-> :storage
  :deep-history-dash -.-> :time
  :deep-history-dash -.-> :track-repo
  :deep-history-runner-jvm -.-> :core:model
  :deep-history-runner-jvm -.-> :coroutines-ext
  :deep-history-runner-jvm -.-> :database:core
  :deep-history-runner-jvm -.-> :deep-history
  :deep-history-runner-jvm -.-> :deep-history-dash
  :network --> :time
  :network-auth -.-> :session
  :network-auth --> :sneak-network
  :network-auth --> :storage
  :network-auth --> :time
  :session --> :storage
  :sneak-network --> :sneak
  :track-repo --> :core:model
  :track-repo --> :database:core
  :track-repo --> :network

classDef android-application fill:#CAFFBF,stroke:#000,stroke-width:2px,color:#000;
classDef compose-library fill:#FFD6A5,stroke:#000,stroke-width:2px,color:#000;
classDef android-dagger fill:#9BF6FF,stroke:#000,stroke-width:2px,color:#000;
classDef android-kotlin fill:#A0C4FF,stroke:#000,stroke-width:2px,color:#000;
classDef android-java fill:#C4A0FF,stroke:#000,stroke-width:2px,color:#000;
classDef jvm-library fill:#BDB2FF,stroke:#000,stroke-width:2px,color:#000;
classDef multiplatform-compose fill:#5FB2FF,stroke:#000,stroke-width:2px,color:#000;
classDef multiplatform-library fill:#BD5FFF,stroke:#000,stroke-width:2px,color:#000;
classDef unknown fill:#FFADAD,stroke:#000,stroke-width:2px,color:#000;
```

<details><summary>📋 Graph legend</summary>

```mermaid
graph TB
  application[application]:::android-application
  composelibrary[composelibrary]:::compose-library
  daggerlibrary[daggerlibrary]:::android-dagger
  androidkotlinlibrary[androidkotlinlibrary]:::android-kotlin
  androidjavalibrary[androidjavalibrary]:::android-java
  jvm[jvm]:::jvm-library
  multiplatformcompose[multiplatformcompose]:::multiplatform-compose
  multiplatform[multiplatform]:::multiplatform-library

  application -.-> feature
  library --> jvm

classDef android-application fill:#CAFFBF,stroke:#000,stroke-width:2px,color:#000;
classDef compose-library fill:#FFD6A5,stroke:#000,stroke-width:2px,color:#000;
classDef android-dagger fill:#9BF6FF,stroke:#000,stroke-width:2px,color:#000;
classDef android-kotlin fill:#A0C4FF,stroke:#000,stroke-width:2px,color:#000;
classDef android-java fill:#C4A0FF,stroke:#000,stroke-width:2px,color:#000;
classDef jvm-library fill:#BDB2FF,stroke:#000,stroke-width:2px,color:#000;
classDef multiplatform-compose fill:#5FB2FF,stroke:#000,stroke-width:2px,color:#000;
classDef multiplatform-library fill:#BD5FFF,stroke:#000,stroke-width:2px,color:#000;
classDef unknown fill:#FFADAD,stroke:#000,stroke-width:2px,color:#000;
```

</details>
<!--endregion-->
