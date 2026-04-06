# `:app`

## Module dependency graph

<!--region graph-->
```mermaid
---
config:
  layout: elk
  elk:
    nodePlacementStrategy: SIMPLE
---
graph TB
  subgraph :core
    direction TB
    :core:model[model]:::unknown
  end
  subgraph :database
    direction TB
    :database:android[android]:::unknown
    :database:core[core]:::unknown
  end
  subgraph :ui
    direction TB
    :ui:destinations[destinations]:::unknown
    :ui:home[home]:::unknown
    :ui:login[login]:::unknown
    :ui:played-tracks[played-tracks]:::unknown
    :ui:refresh-token-entry[refresh-token-entry]:::unknown
    :ui:settings[settings]:::unknown
  end
  :time[time]:::unknown
  :storage[storage]:::unknown
  :sneak-network[sneak-network]:::unknown
  :sneak[sneak]:::unknown
  :session[session]:::unknown
  :network-auth[network-auth]:::unknown
  :network[network]:::unknown
  :logger-bugsnag[logger-bugsnag]:::unknown
  :logger[logger]:::unknown
  :history-recorder[history-recorder]:::unknown
  :foreman[foreman]:::unknown
  :di[di]:::unknown
  :app-theme[app-theme]:::unknown
  :app-core[app-core]:::unknown
  :app[app]:::unknown
  :android-app-res[android-app-res]:::unknown

  :app -.-> :app-core
  :app -.-> :database:android
  :app -.-> :database:core
  :app -.-> :di
  :app -.-> :history-recorder
  :app -.-> :logger
  :app -.-> :logger-bugsnag
  :app -.-> :network
  :app -.-> :network-auth
  :app -.-> :session
  :app -.-> :sneak
  :app -.-> :sneak-network
  :app -.-> :storage
  :app -.-> :time
  :app-core -.-> :android-app-res
  :app-core -.-> :app-theme
  :app-core -.-> :network-auth
  :app-core -.-> :session
  :app-core -.-> :time
  :app-core -.-> :ui:destinations
  :app-core -.-> :ui:home
  :app-core -.-> :ui:login
  :app-core -.-> :ui:played-tracks
  :app-core -.-> :ui:refresh-token-entry
  :app-core -.-> :ui:settings
  :database:android -.-> :database:core
  :foreman -.-> :history-recorder
  :foreman -.-> :logger
  :foreman -.-> :storage
  :foreman -.-> :time
  :logger-bugsnag -.-> :logger
  :ui:home -.-> :android-app-res
  :ui:home -.-> :app-theme
  :ui:home -.-> :foreman
  :ui:home -.-> :session
  :ui:login -.-> :app-theme
  :ui:login -.-> :network-auth
  :ui:login -.-> :session
  :ui:login -.-> :time
  :ui:played-tracks -.-> :app-theme
  :ui:played-tracks -.-> :core:model
  :ui:played-tracks -.-> :database:core
  :ui:refresh-token-entry -.-> :android-app-res
  :ui:refresh-token-entry -.-> :app-theme
  :ui:refresh-token-entry -.-> :di
  :ui:refresh-token-entry -.-> :network-auth
  :ui:refresh-token-entry -.-> :ui:destinations
  :ui:settings -.-> :android-app-res
  :ui:settings -.-> :app-theme
  :ui:settings -.-> :foreman
  :ui:settings -.-> :network-auth

classDef android-application fill:#CAFFBF,stroke:#000,stroke-width:2px,color:#000;
classDef android-feature fill:#FFD6A5,stroke:#000,stroke-width:2px,color:#000;
classDef android-library fill:#9BF6FF,stroke:#000,stroke-width:2px,color:#000;
classDef android-test fill:#A0C4FF,stroke:#000,stroke-width:2px,color:#000;
classDef jvm-library fill:#BDB2FF,stroke:#000,stroke-width:2px,color:#000;
classDef unknown fill:#FFADAD,stroke:#000,stroke-width:2px,color:#000;
```

<details><summary>📋 Graph legend</summary>

```mermaid
graph TB
  application[application]:::android-application
  feature[feature]:::android-feature
  library[library]:::android-library
  jvm[jvm]:::jvm-library

  application -.-> feature
  library --> jvm

classDef android-application fill:#CAFFBF,stroke:#000,stroke-width:2px,color:#000;
classDef android-feature fill:#FFD6A5,stroke:#000,stroke-width:2px,color:#000;
classDef android-library fill:#9BF6FF,stroke:#000,stroke-width:2px,color:#000;
classDef android-test fill:#A0C4FF,stroke:#000,stroke-width:2px,color:#000;
classDef jvm-library fill:#BDB2FF,stroke:#000,stroke-width:2px,color:#000;
classDef unknown fill:#FFADAD,stroke:#000,stroke-width:2px,color:#000;
```

</details>
<!--endregion-->
