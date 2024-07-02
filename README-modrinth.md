<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/ShardMC/arte">
    <img src="https://raw.githubusercontent.com/ShardMC/Arte/main/images/logo-upscaled.png" alt="Logo" width="96" height="96">
  </a>

  <h3 align="center">Arte</h3>

  <p align="center">
    Send server resource packs in spoon-sized bites!
    <br />
    <a href="https://shardmc.github.io/projects/arte"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/ShardMC/arte">View Demo</a>
    ·
    <a href="https://github.com/ShardMC/arte/issues">Report Bug</a>
    ·
    <a href="https://github.com/ShardMC/arte/issues">Request Feature</a>
  </p>

  ![Spigot][spigot-shield]
  ![Fabric][fabric-shield]
  ![No Forge][noforge-shield]
</div>

> **NOTE: Arte works only on Minecraft 1.20.3+**

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About Arte</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About Arte

<!--[![Arte Speed Comparison][speed-comparison]][github-url]-->

So, what does it do? Simple! Let's say you have a BIG server resource pack.
Every time you make a little change, you will have to manually re-host it somewhere, and players will have to fully re-download it.
This can get annoying. Arte splits the resource pack into parts (by namespaces), hosts it directly on your server
and sends each one as a different server resource pack!
> Q: How is this possible? There can only be one server resource pack loaded at one time!

> A: In 1.20.3 Mojang added a new feature that allows servers to send multiple server resource packs at a time! 

Players will only re-download the parts that actually need updating, and apply other server packs.
<!--(Tip: Arte can be combined with [Resource Pack Tweaks][rptweaks-url] mod for best server resource pack behaviour).-->



<!-- GETTING STARTED -->
## Getting Started

To set up Arte on your server follow these steps.

### Prerequisites

Different mod-loader require different dependencies!
#### On Fabric:
[![Requires Fabric API][fabric-api-shield]][fabric-api-url]

#### On Spigot:
* Requires nothing!

#### On Paper:
* Requires [ProtocolLib][protocol-url].

### Installation
[![Hangar][hangar-shield]][hangar-url]
[![GitHub][github-shield]][github-url]
[![Polymart][polymart-shield]][polymart-url]
[![Spigot][spigot-shield]][spigot-url]
<!--
[![Hangar][hangar-shield]][hangar-url]
[![GitHub][github-shield]][github-url]
[![Polymart][polymart-shield]][polymart-url]
[![CurseForge][curseforge-shield]][curseforge-url]
[![Spigot][spigot-shield]][spigot-url]
-->

_Download and install the mod or plugin from [Modrinth][modrinth-versions-url], [Hangar][hangar-versions-url], [Polymart][polymart-updates-url]<!--, [CurseForge][curseforge-updates-url]-->, [Spigot][spigot-updates-url] or [GitHub Releases][github-releases-url]._

1. Start your server
2. Open Arte's config file
3. Set `address` to your server's public address
4. Open the port specified in config (`1864`) or put already opened port in the `port` field in config.
5. Restart your server



<!-- USAGE EXAMPLES -->
## Usage

Put your resource pack source (not zipped) in the `arte/resourcepack/` folder and run `/arte reload`.
> Note: Arte folder can be different depending on your server! If you use Fabric or Quilt, it's folder will be in server's folder.
> If you use Spigot, then Arte's folder will be located in `plugins/arte/`.

_For more examples, please refer to the [Documentation](https://shardmc.github.io/projects/arte/about)._



<!-- CONTACT -->
## Contact

[![Discord][discord-shield]][discord-url]



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[spigot-shield]: https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/supported/spigot_64h.png
[fabric-shield]: https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/supported/fabric_64h.png
[noforge-shield]: https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/unsupported/forge_64h.png

[rptweaks-url]: https://modrinth.com/mod/resource-pack-tweaks

[fabric-api-shield]: https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/requires/fabric-api_64h.png
[fabric-api-url]: https://modrinth.com/mod/fabric-api
[protocol-url]: https://ci.dmulloy2.net/job/ProtocolLib//lastBuild/

[spigot-url]: https://www.spigotmc.org/resources/arte.114150/

[github-shield]: https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/available/github_64h.png
[github-url]: https://github.com/ShardMC/arte
[hangar-shield]: https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/available/hangar_64h.png
[hangar-url]: https://hangar.papermc.io/shardmc/arte
[polymart-shield]: https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/available/polymart_64h.png
[polymart-url]: https://polymart.org/resource/plugin-arte.5242
[curseforge-shield]: https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/available/curseforge_64h.png
[curseforge-url]: https://curseforge.com/minecraft/bukkit-plugins/arte-for-spigot
[spigot-shield]: https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/supported/spigot_64h.png
[spigot-url]: https://www.spigotmc.org/resources/arte.114150/

[modrinth-versions-url]: https://modrinth.com/plugin/arte/versions
[hangar-versions-url]: https://hangar.papermc.io/shardmc/arte/versions
[polymart-updates-url]: https://polymart.org/resource/arte.5242/updates
[curseforge-updates-url]: https://curseforge.com/minecraft/bukkit-plugins/arte-for-spigot/files
[spigot-updates-url]: https://www.spigotmc.org/resources/arte.114150/updates
[github-releases-url]: https://github.com/ShardMC/arte/releases

[github-issues-url]: https://github.com/ShardMC/arte/issues

[discord-shield]: https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/social/discord-plural_64h.png
[discord-url]: https://discord.gg/CqVAzakrqx
