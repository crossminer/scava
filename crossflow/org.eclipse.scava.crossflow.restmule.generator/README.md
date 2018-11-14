
# RestMule OpenAPI Client Generator #

RestMule is a framework for handling various service policies, such as limited number of requests within a period of time and multi-page responses, by generating resilient clients that are able to handle request rate limits, network failures, response caching, and paging in a graceful and transparent manner.

### How do I generate a resilient client from an OpenAPI Specification? ###

To generate the GitHub Resilient Client you need to import the `dependencies/emc-json` driver into your eclipse workspace and run a new eclipse instance. In the new eclipse you need to import the `org.eclipse.scava.crossflow.restmule.generator` plugin and execute the run configuration `generateFromOAS.launch` which will generate the code from the OpenAPI specification  found in the `schemas` folder.

#### Detailed steps:

1) Run `RESTMULE-CLIENT-GENERATOR-ECLIPSE-APPLICATION-LAUNCHER.launch` (will open a new Eclipse instance).

2) Import `org.eclipse.scava.crossflow.restmule.generator` into workspace of Eclipse instance launched in the previous step.

3) Add new or uncomment existing OpenAPI property specification in `build.xml` file. For example:

<!-- GitHub API version 3 -->
<property  name="api"  value="github"/>
<property  name="json.model.file"  value="schemas/github_v3.json"/>

4) Make sure an `.eol` file that exactly matches the named provided in belows property value for `json.model.file` is available inside
 	the directory `epsilon/util/fix`. For example, `github.eol` for the property specified in the previous step.

5) Run `generateFromOAS.launch` (will display console output) to produce the RestMule client for the specified OpenAPI. For example, the project `org.eclipse.scava.crossflow.restmule.client.github` is generated when taking into account the example specified in the previous steps.


### Literature: ###

Beatriz A. Sanchez, Konstantinos Barmpis, Patrick Neubauer, Richard F. Paige, Dimitrios S. Kolovos:
Restmule: enabling resilient clients for remote APIs. MSR 2018: 537-541.
[ACM electronic edition](http://doi.acm.org/10.1145/3196398.3196405)
[Download preprint](https://beatrizsanchez.github.io/publications/MSR2018.pdf)
