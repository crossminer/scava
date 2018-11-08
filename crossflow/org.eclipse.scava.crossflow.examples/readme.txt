Build the jar using build-jar.xml

Running the first commitment example (produces 10 instances of 3 animals, needs 3 workers)

- Run the master: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.firstcommitment.FirstCommitmentExample -name Master
- Run worker1: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.firstcommitment.FirstCommitmentExample -mode worker -name Worker1
- Run worker2: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.firstcommitment.FirstCommitmentExample -mode worker -name Worker2

-------------------------

Running the first commitment-based GitHub repositories example (produces 290 repository instances, needs 3 workers including master)

- Make sure to import the following projects into active Eclipse workspace: 
  - org.eclipse.scava.crossflow.restmule.core
  - org.eclipse.scava.crossflow.restmule.github
  
- Run master: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.GhRepoExample
- Run worker1: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.GhRepoExample -mode worker -name Worker1
- Run worker2: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.firstcommitment.ghrepo.GhRepoExample -mode worker -name Worker2


--------- UNDER CONSTRUCTION (BELOW) ---------

Running the first commitment-based GitHub repositories example (produces 290 repository instances, needs 3 workers including master)

- Make sure to import the following projects into active Eclipse workspace: 
  - org.eclipse.scava.crossflow.restmule.core
  - org.eclipse.scava.crossflow.restmule.github
  
- Run master: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.MdeTechnologyExample
- Run worker1: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.MdeTechnologyExample -mode worker -name Worker1
- Run worker2: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.firstcommitment.mdetech.MdeTechnologyExample -mode worker -name Worker2

-------------------------

Running the opinion-based GitHub MDE technologies example (produces 10 instances of 3 technologies, needs 3 workers)

- Run the master: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.opinionated.ghmde.GhMdeTechExampleMasterWorkers -name Master
- Run worker1: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.opinionated.ghmde.GhMdeTechExample -mode worker -name Worker1
- Run worker2: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.opinionated.ghmde.GhMdeTechExampleMasterWorkers -mode worker -name Worker2

-------------------------

Running the opinion-based GitHub repositories example (produces 10 instances of 3 repositories, needs 3 workers)

- Run the master: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.opinionated.ghrepo.GhRepoExample -name Master
- Run worker1: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.opinionated.ghrepo.GhRepoExample -mode worker -name Worker1
- Run worker2: java -cp org.eclipse.scava.crossflow.examples.jar org.eclipse.scava.crossflow.examples.opinionated.ghrepo.GhRepoExample -mode worker -name Worker2
