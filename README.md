<a name="readme-top"></a>

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]
[![Discord][discord-shield]][discord-url]
[![Modrinth][modrinth-shield]][modrinth-url]

<br />
<div align="center">
  <a href="https://github.com/syorito-hatsuki/death-counter">
    <img src="https://github.com/syorito-hatsuki/death-counter/blob/1.20/src/main/resources/assets/deathcounter/icon.png?raw=true" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">Death Counter</h3>

  <p align="center">
    Death Counter with client Web UI
    <br />
    <a href="https://discord.gg/pbwnMwnUD6">Support</a>
    ·
    <a href="https://github.com/syorito-hatsuki/death-counter/issues">Report Bug</a>
    ·
    <a href="https://github.com/syorito-hatsuki/death-counter/issues">Request Feature</a>
  </p>
</div>

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#usage">Usage</a>
      <ul>
        <li><a href="#gameplay">Gameplay</a></li>
        <li>
          <a href="#commmands">Commmands</a>
          <ul>
            <li><a href="#client-side-commands">Client-side commands</a></li>
            <li><a href="#server-side-commands">Server-side commands</a></li>
          </ul>
        </li>
        <li><a href="#client-config">Client Config</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>

## About The Project

Death Counter is a server-side Minecraft Fabric mod to get info about how many players die, directly from player stats,
so you always get an actual count. Also support for client-side WebUI, useful for streamers. You can also see it in-game
using commands.

> Other mods only start counting from zero after it's been installed. This mod, on the other hand, can also report
> deaths from before this mod was installed.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

* ![Fabric][fabric]
* ![Fabric-Language-Kotlin][fabric-language-kotlin]
* ![Ducky-Updater-Lib][ducky-updater-lib]
* ![fStats][fstats]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Usage

### Gameplay

For start use it u don't need any special setup. Plug and Play :)

### Commmands

#### Client-side commands

| Command                       | OP | Permission | Description                                                            |
|-------------------------------|----|------------|------------------------------------------------------------------------|
| `/dcc`                        | ❌  | `none`     | Get your own death count                                               |
| `/dcc warning <boolean>`      | ❌  | `none`     | Enable/Disable warning message when mod unavailable on server-side     |
| `/dcc webuinotify <boolean>`  | ❌  | `none`     | Enable/Disable web client address toast notification on login to world |
| `/dcc title <boolean>`        | ❌  | `none`     | Enable/Disable title message                                           |
| `/dcc delayInTicks <integer>` | ❌  | `none`     | Change title message show delay                                        |
| `/dcc chat <boolean>`         | ❌  | `none`     | Enable/Disable chat message                                            |

#### Server-side commands

| Command               | OP | Permission | Description                        |
|-----------------------|----|------------|------------------------------------|
| `/dcs top`            | ❌  | `none`     | Get top of dead inside :D          |
| `/dcs <page>`         | ❌  | `none`     | Get the death count of all players |
| `/dcs [<playerName>]` | ❌  | `none`     | Get a specific player death count  |

### Client config

```json5
{
  "titleMessage": {
    "disable": false,
    "delayInTicks": 60
  },
  "chatMessage": {
    "disable": false
  },
  // Warning message when mod unavailable on server-side
  "showWarning": true,
  // Web client toast notification
  "showToastNotification": {
    "disable": false,
    "delay": 5000
  },
  "webSetup": {
    // Local WebUI address
    "localAddress": "0.0.0.0",
    // Local WebUI port (1024-49151)
    "servicePort": 1540,
    // Local WebUI refresh time in milliseconds (more is better)
    "refreshDelayMs": 5000
  }
}
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Roadmap

- [x] [Client-Side WebUI for streamers](https://github.com/syorito-hatsuki/death-counter/blob/1.20/obs-integration.md)

See the [open issues](https://github.com/syorito-hatsuki/death-counter/issues) for a full list of proposed features (and
known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any
contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also
simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

[contributors-shield]: https://img.shields.io/github/contributors/syorito-hatsuki/death-counter.svg?style=for-the-badge

[contributors-url]: https://github.com/syorito-hatsuki/death-counter/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/syorito-hatsuki/death-counter.svg?style=for-the-badge

[forks-url]: https://github.com/syorito-hatsuki/death-counter/network/members

[stars-shield]: https://img.shields.io/github/stars/syorito-hatsuki/death-counter.svg?style=for-the-badge

[stars-url]: https://github.com/syorito-hatsuki/death-counter/stargazers

[issues-shield]: https://img.shields.io/github/issues/syorito-hatsuki/death-counter.svg?style=for-the-badge

[issues-url]: https://github.com/syorito-hatsuki/death-counter/issues

[license-shield]: https://img.shields.io/github/license/syorito-hatsuki/death-counter.svg?style=for-the-badge

[license-url]: https://github.com/syorito-hatsuki/death-counter/blob/master/LICENSE.txt

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://linkedin.com/in/kit-lehto

[fabric]: https://img.shields.io/badge/fabric%20api-DBD0B4?style=for-the-badge

[fabric-language-kotlin]: https://img.shields.io/badge/fabric%20language%20kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white

[ducky-updater-lib]: https://img.shields.io/badge/ducky%20updater%20lib-1bd96a?style=for-the-badge

[fstats]: https://img.shields.io/badge/fStats-111111?style=for-the-badge

[discord-shield]: https://img.shields.io/discord/1032138561618726952?logo=discord&logoColor=white&style=for-the-badge&label=Discord

[discord-url]: https://discord.gg/pbwnMwnUD6

[modrinth-shield]: https://img.shields.io/modrinth/v/death-counter?label=Modrinth&style=for-the-badge

[modrinth-url]: https://modrinth.com/mod/death-counter
