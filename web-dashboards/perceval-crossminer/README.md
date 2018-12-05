# perceval-crossminer

Bundle of Perceval backends for CROSSMINER ecosystem.

## Backends

The backends currently managed by this package support:

* OSSMETER API REST

## Requirements

* Python >= 3.4
* python3-requests >= 2.7
* grimoirelab-toolkit >= 0.1
* perceval >= 0.9.11

## Installation

To install this package you will need to clone the repository first:

```
$ git clone https://github.com/crossminer/scava.git
```

In this case, [setuptools](http://setuptools.readthedocs.io/en/latest/) package will be required.
Make sure it is installed before running the next commands:

```
$ cd web-dashboards/perceval-crossminer
$ pip3 install -r requirements.txt
$ python3 setup.py install
```

## Using the CROSSMINER platform

To test the crossminer perceval backend you can deploy CROSSMINER in your host using docker.

Follow:

[Install OSSMeter services](https://github.com/crossminer/scava/tree/master/web-dashboards#init-ossmeter-mongodb-with-grimoirelab-data)

Then you can just access the OSSMeter REST API in [http://localhost:8192](http://localhost:8192) 


## Examples

### CROSSMINER

```
$ perceval crossminer <url> <project>
```

## License

Licensed under GNU General Public License (GPL), version 3 or later.
