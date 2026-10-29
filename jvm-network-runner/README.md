# `:jvm-network-runner`

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
  :time[time]:::jvm-library
  :storage[storage]:::jvm-library
  :sneak-network[sneak-network]:::jvm-library
  :sneak[sneak]:::jvm-library
  :session[session]:::jvm-library
  :network-auth[network-auth]:::jvm-library
  :network[network]:::jvm-library
  :jvm-network-runner[jvm-network-runner]:::jvm-library
  :history-recorder[history-recorder]:::jvm-library
  :coroutines-ext[coroutines-ext]:::jvm-library

  :database:jvm -.-> :database:core
  :history-recorder -.-> :core:model
  :history-recorder --> :database:core
  :history-recorder --> :network
  :jvm-network-runner -.-> :coroutines-ext
  :jvm-network-runner -.-> :database:core
  :jvm-network-runner -.-> :database:jvm
  :jvm-network-runner -.-> :history-recorder
  :jvm-network-runner -.-> :network
  :jvm-network-runner -.-> :network-auth
  :jvm-network-runner -.-> :session
  :jvm-network-runner -.-> :sneak
  :jvm-network-runner -.-> :sneak-network
  :jvm-network-runner -.-> :storage
  :jvm-network-runner -.-> :time
  :network --> :time
  :network-auth -.-> :session
  :network-auth --> :sneak-network
  :network-auth --> :storage
  :network-auth --> :time
  :session --> :storage
  :sneak-network --> :sneak

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
