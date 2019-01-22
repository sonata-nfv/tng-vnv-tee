[![Build Status](http://jenkins.sonata-nfv.eu/buildStatus/icon?job=tng-vnv-tee/master)](https://jenkins.sonata-nfv.eu/job/tng-vnv-tee)

# Test Execution Engine for 5GTANGO Verification and Validation
This is a [5GTANGO](http://www.5gtango.eu) component to execute the test suites for 5G Network Services.


<p align="center"><img src="https://github.com/sonata-nfv/tng-api-gtw/wiki/images/sonata-5gtango-logo-500px.png" /></p>

## What it is

The TEE is responsible for the execution of individual test plans generated from the LCM. Currently it supports 3 test plugin types:

1. _bash tester_ - A general bash script executor
1. _wrk tester_ - A test plugin developed to report results from the [WRK](https://github.com/wg/wrk) benchmark application
1. _TTCN-3 tester_ - A test adapter designed to run [TTCN-3](http://www.ttcn-3.org/) scripts

<p align="center"><img src="https://raw.githubusercontent.com/wiki/sonata-nfv/tng-vnv-lcm/images/v40-release-lcm.png" /></p>

More details on how to use these plugins are available in the [wiki](https://github.com/sonata-nfv/tng-vnv-tee/wiki/Test-Execution-Engine-tester-support)

## Build from source code

```bash
./gradlew
```

The project depends on _java_ and _docker_ to build. Once you have those two tools, you simply run `./gradlew` command without parameters to do a full build:
* clean : clean the project build directory
* compile code
* process resources
* process resources
* package jar
* compile test
* process test resources
* execute test
* execute docker build
* execute docker push: optional, default to
  * `true` on jenkins build
  * `false` on local build

For advanced build arguments, please checkout the [gradle-buildscript](https://github.com/mrduguo/gradle-buildscript) project.


## Run the docker image

```bash
docker pull registry.sonata-nfv.eu:5000/tng-vnv-tee
docker run -d \
    --name tng-vnv-tee \
    -p 6200:6200 \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v $(which docker):/usr/bin/docker \
    -v tee:/workspace \
    -e APP_GK_HOST=172.31.6.42 \
    registry.sonata-nfv.eu:5000/tng-vnv-tee
```

### Health Checking

Once the component has started, you can access the component health endpoint at (change IP Addresses depending on your docker setup):

_http://192.168.99.100:6200/tng-vnv-tee/health_

### Swagger UI


* static
    * http://petstore.swagger.io/?url=https://raw.githubusercontent.com/sonata-nfv/tng-vnv-tee/master/src/main/resources/static/swagger.json
    * http://petstore.swagger.io/?url=https://raw.githubusercontent.com/sonata-nfv/tng-vnv-tee/master/src/main/resources/static/swagger-dependencies.json
* pre integration 
    * http://172.31.6.29:6200/tng-vnv-tee/swagger-ui.html
* local 
    * http://192.168.99.100:6200/tng-vnv-tee/swagger-ui.html


## Contributing
Contributing to the Gatekeeper is really easy. You must:

1. Clone [this repository](http://github.com/sonata-nfv/tng-vnv-tee);
1. Work on your proposed changes, preferably through submitting [issues](https://github.com/sonata-nfv/tng-vnv-tee/issues);
1. Submit a Pull Request;
1. Follow/answer related [issues](https://github.com/sonata-nfv/tng-vnv-tee/issues) (see Feedback-Channel, below).



## Dependencies

No specific libraries are required for building this project. The following tools are used to build the component

- `java (version 8)`
- `gradle (version 4.9)`
- `docker (version 18.x)`


## License

This 5GTANGO component is published under Apache 2.0 license. Please see the [LICENSE](LICENSE) file for more details.

## Lead Developers

The following lead developers are responsible for this repository and have admin rights. They can, for example, merge pull requests.

* George Andreou ([allemaos](https://github.com/allemaos))
* Guo Du ([mrduguo](https://github.com/mrduguo))
* Felipe Vicens ([felipevicens](https://github.com/felipevicens))

## Feedback-Channels

Please use the [GitHub issues](https://github.com/sonata-nfv/tng-vnv-tee/issues) and the 5GTANGO Verification and Validation group mailing list `5gtango-dev@list.atosresearch.eu` for feedback.
[![Join the chat at https://gitter.im/sonata-nfv/Lobby](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/sonata-nfv/Lobby)
