# Transportation Gateway Deployment System
<!-- Add banner here -->
![Banner](./images/1.png)

<p align="center">
  <sub>
    Built by
    <a href="https://github.com/drggboy">drggboy</a>,
    <a href="https://github.com/grtm77">grtm77</a>
  </sub>
</p>

<p align="center">
  <a href="" target="_blank">
    <img alt="jdk release" src="https://img.shields.io/badge/jdk-1.8-blue">
  </a>
  <a href="" target="_blank">
    <img alt="Nodejs" src="https://img.shields.io/badge/Nodejs-v16.5.1-blue">
  </a>
  <a href="" target="_blank">
    <img alt="npm" src="https://img.shields.io/badge/npm-9.6.5-blue">
  </a>
  <a href="" target="_blank">
    <img alt="matlab release" src="https://img.shields.io/badge/matlab-R2016b-blue">
  </a>
  </br>
  <a href="https://github.com/drggboy/mytest_v2/releases" target="_blank">
    <img alt="GitHub release" src="https://img.shields.io/github/v/release/drggboy/mytest_v2?include_prereleases&style=flat-square">
  </a>

  <a href="https://github.com/drggboy/mytest_v2/commits/master" target="_blank">
    <img src="https://img.shields.io/github/last-commit/drggboy/mytest_v2?style=flat-square" alt="GitHub last commit">
  </a>

  <!-- <a href="https://github.com/drggboy/mytest_v2/issues" target="_blank">
    <img src="https://img.shields.io/github/issues/drggboy/mytest_v2?style=flat-square&color=red" alt="GitHub issues">
  </a> -->

  <!-- <a href="https://github.com/drggboy/mytest_v2/pulls" target="_blank">
    <img src="https://img.shields.io/github/issues-pr/drggboy/mytest_v2?style=flat-square&color=blue" alt="GitHub pull requests">
  </a> -->

  <a href="https://github.com/drggboy/mytest_v2/graphs/contributors" target="_blank">
    <img alt="Contributors" src="https://img.shields.io/badge/all_contributors-2-orange.svg?style=flat-square">
  </a>


  <a href="https://github.com/drggboy/mytest_v2/blob/master/LICENSE" target="_blank">
    <img alt="LICENSE" src="https://img.shields.io/github/license/drggboy/mytest_v2?style=flat-square&color=yellow">
  <a/>
</p>
<hr>

<!-- Remove this note if you plan to copy this README -->


# Table of Contents
- [Transportation Gateway Deployment System](#transportation-gateway-deployment-system)
- [Table of Contents](#table-of-contents)
- [Demo](#demo)
- [Usage](#usage)
- [Environment Configuration](#environment-configuration)
  - [runtime environment](#runtime-environment)
  - [development environment](#development-environment)
- [Deployment steps](#deployment-steps)
- [Directory Structure Description](#directory-structure-description)
- [Contributors](#contributors)
- [License](#license)

# Demo

<!-- Add a demo for your project -->

This system is an experimental platform for simulating and analyzing roadside traffic. It uses the data resources and traffic information provided by Baidu Maps API to simulate the roadside traffic environment and collect relevant data. It can be used to evaluate the implementation effect of the relay node deployment algorithm in actual traffic scenarios.

![Banner](./images/test.gif)

Gateway deployment planning consists of two key steps: roadside sensor node coordinate collection and relay node optimal deployment location calculation. This system provides 8 test [datasets](https://github.com/drggboy/mytest_v2/tree/master/Datasets) that can be used for testing. You can also use the mark mode in the system to mark node coordinates for relay node calculations.


# Usage
If you only need to run this project for traffic gateway deployment, then only need to configure [runtime environment](#runtime-environment)。You can download the version that does not include the matlab algorithm in the release. After downloading, execute the following code to deploy the gateway by accessing `localhost:8081`.
```sh
java xxx.jar -ak xxxxx
```

This project supports calling matlab algorithm for calculation, but needs to configure the [matlab runtime environment]().


# Environment Configuration
## runtime environment
* JRE 1.8
* MATLAB Runtime R2016b (9.1)

## development environment
If you need to modify the back-end code or modify the front-end page, you need to configure the following environment
* Java Version: Temurin JDK 1.8
* Node.js Version：v16.15.1
* npm Version：9.6.5
* vite Version: 4.4.4

# Deployment steps

Clone this repository and navigate inside the project root folder.

If you want to modify the style of the front end, please enter the project root directory, and then execute the following code.
```sh
cd ./vue
npm install
```
After installing the dependencies, you can modify the front-end related code in `mytestv2/vue/src/App.vue`, and then execute the following command to view the front-end interface.
```sh
npm run dev
```
After the front-end interface is modified, you can compile the static file.
```sh
npm run build
```
Then a static file will be generated in the dist directory, and the spring boot project can be started after moving it to the`mytest_v2/src/main/resources/static `directory, which can be moved directly through the script
```python
python .\move_build_output.py
```
>The move_build_output.py file will automatically replace the compiled static files in your project. Additionally, it modifies a specific line within the [root project]\src\main\resources\static\index.html file to ensure that the latest version of the static files is served by the Spring Boot application. This automation streamlines the process of updating your web application's frontend without manually handling file transfers or edits.

# Directory Structure Description
```sh
|-- README.md     // help
|-- src           // Backend processing related
|-- vue           // Front-end vue code
|-- images
|-- mvnw
|-- mvnw.cmd
|-- pom.xml
|-- LICENSE
```

# Contributors

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/drggboy"><img src="https://avatars.githubusercontent.com/u/47265146?v=4?s=100" width="100px;" alt="drggboy"/><br /><sub><b>drggboy</b></sub></a><br /><a href="https://github.com/grtm77/mytest_v2/commits?author=drggboy" title="Code">💻</a> <a href="https://github.com/grtm77/mytest_v2/commits?author=drggboy" title="Documentation">📖</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/grtm77"><img src="https://avatars.githubusercontent.com/u/50659884?v=4?s=100" width="100px;" alt="grtm77"/><br /><sub><b>grtm77</b></sub></a><br /><a href="https://github.com/grtm77/mytest_v2/commits?author=grtm77" title="Code">💻</a> <a href="https://github.com/grtm77/mytest_v2/commits?author=grtm77" title="Documentation">📖</a></td>
    </tr>
  </tbody>
  <tfoot>
    <tr>
      <td align="center" size="13px" colspan="7">
        <img src="https://raw.githubusercontent.com/all-contributors/all-contributors-cli/1b8533af435da9854653492b1327a23a4dbd0a10/assets/logo-small.svg">
          <a href="https://all-contributors.js.org/docs/en/bot/usage">Add your contributions</a>
        </img>
      </td>
    </tr>
  </tfoot>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!


# License
> **Note**: For longer README files, I usually add a "Back to top" buttton as shown above. It makes it easy to navigate.

<!-- [(Back to top)](#目录) -->

[MIT](./LICENSE) license.
