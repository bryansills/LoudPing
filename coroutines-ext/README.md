# `:coroutines-ext`

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
  :coroutines-ext[coroutines-ext]:::jvm-library

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
