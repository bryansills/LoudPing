# `:app-core`

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
  subgraph :database
    direction TB
    :database:core[core]:::unknown
  end
  subgraph :core
    direction TB
    :core:model[model]:::jvm-library
  end
  subgraph :ui
    direction TB
    :ui:destinations[destinations]:::jvm-library
    :ui:home[home]:::compose-library
    :ui:login[login]:::android-dagger
    :ui:played-tracks[played-tracks]:::compose-library
    :ui:refresh-token-entry[refresh-token-entry]:::compose-library
    :ui:settings[settings]:::compose-library
  end
  :time[time]:::jvm-library
  :storage[storage]:::jvm-library
  :sneak-network[sneak-network]:::jvm-library
  :sneak[sneak]:::jvm-library
  :session[session]:::jvm-library
  :network-auth[network-auth]:::jvm-library
  :network[network]:::jvm-library
  :logger[logger]:::jvm-library
  :history-recorder[history-recorder]:::jvm-library
  :foreman[foreman]:::android-dagger
  :di[di]:::jvm-library
  :app-theme[app-theme]:::multiplatform-compose
  :app-core[app-core]:::compose-library
  :android-app-res[android-app-res]:::android-java

  :app-core -.-> :app-theme
  :app-core -.-> :ui:destinations
  :app-core -.-> :ui:home
  :app-core -.-> :ui:login
  :app-core -.-> :ui:played-tracks
  :app-core -.-> :ui:refresh-token-entry
  :app-core -.-> :ui:settings
  :foreman --> :history-recorder
  :foreman --> :logger
  :foreman --> :storage
  :foreman --> :time
  :history-recorder -.-> :core:model
  :history-recorder --> :database:core
  :history-recorder --> :network
  :network --> :time
  :network-auth -.-> :session
  :network-auth --> :sneak-network
  :network-auth --> :storage
  :network-auth --> :time
  :session --> :storage
  :sneak-network --> :sneak
  :ui:home -.-> :android-app-res
  :ui:home -.-> :app-theme
  :ui:home --> :foreman
  :ui:home --> :session
  :ui:login -.-> :app-theme
  :ui:login --> :network-auth
  :ui:login --> :time
  :ui:played-tracks -.-> :app-theme
  :ui:played-tracks --> :core:model
  :ui:played-tracks --> :database:core
  :ui:refresh-token-entry --> :di
  :ui:refresh-token-entry --> :network-auth
  :ui:refresh-token-entry -.-> :ui:destinations
  :ui:settings -.-> :android-app-res
  :ui:settings -.-> :app-theme
  :ui:settings --> :foreman
  :ui:settings --> :network-auth

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
