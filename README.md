# Death Counter
<a title="Fabric Language Kotlin" href="https://minecraft.curseforge.com/projects/fabric-language-kotlin" target="_blank" rel="noopener noreferrer"><img style="display: block; margin-left: auto; margin-right: auto;" src="https://i.imgur.com/c1DH9VL.png" alt="" width="230" /></a>
<img src="https://i.imgur.com/iaETp3c.png" alt="" width="200" >
<img src="https://i.imgur.com/Ol1Tcf8.png" alt="" width="200" >

## Description
// TODO

## Commands and permission
|            Command           |OP|Permission|              Description           |
|------------------------------|----|----------|------------------------------------|
|`/deathcounter`|❌|`deathcounter.todo`|All available commands|
|`/deathcounter all`|❌|`deathcounter.todo`|List of available icons ID|
|`/deathcounter [<playerName>]`|❌|`deathcounter.todo`|List own markers|

## Client config
```json5
{
    "ip": "0.0.0.0",  // Local WebUI address (Recommend keep default)
    "port": 3000,     // Local WebUI port (1024-49151)
    "msDelay": 5000   // Local WebUI refresh time in seconds (more is better)
}
```

## Web
### Client side WebUI
WebUI endpoint `http://0.0.0.0:3000`
|Request|Path|Description|
|:-:|-|-|
|GET|`/`|Return own death count|
|GET|`/{playerName}`|Return count of death by playerName|

### Server side API
API endpoint is `http://yourServerIP*:1540`
> *yourServerIP = server-ip from server.properties

|Request|Path|Description|
|:-:|-|-|
|GET|`/`|Return Map<String, Int> object with all players|
|GET|`/{playerName}`|Return Int count of death by playerName|

## Tips
### Adding counter in OBS
// TODO
