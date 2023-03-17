# Contributing to SMASSIF-RML

We would love for you to contribute to **SMASSIF-RML** and help make it even better than it is today!

Please contribute to this repository if any of the following is true:

- You have expertise in *Semantic Web*, *Knowledge Graph Construction* or *RDF Stream Reasoning*,
- You have expertise in *reasoning systems*, *data wrangling*, *message passing*, or *network protocols*,
- You want to contribute to the development of *Semantic Web technologies* and want to help lower the burden of managing *complex technical systems*.

## How to contribute

Prerequisites:

- Knowledge of [Semantic Web](https://en.wikipedia.org/wiki/Semantic_Web) technologies, notably: [RML](https://rml.io/), [JSON-LD](https://json-ld.org/), [Terse RDF Triple Language (Turtle)](https://en.wikipedia.org/wiki/Turtle_(syntax)).
- Knowledge of *object programming*, notably the Java programming language ([OpenJDK](https://openjdk.org/), [Oracle](https://www.java.com)) and the [Maven](https://maven.apache.org/) packaging tool.
- Familiarity with [pull requests](https://help.github.com/articles/using-pull-requests) and [issues](https://guides.github.com/features/issues/).
- Familiarity with [test design](https://en.wikipedia.org/wiki/Test_design) and techniques.
- Knowledge of [Markdown](https://help.github.com/articles/markdown-basics/) for editing `.md` documents.

In particular, we seek the following types of contributions:

- **Improving & Extending**: participate in making *SMASSIF-RML* code smoother and more robust, for example by:
  enabling the triggering of a given set of pipelines based on CLI argument or specific configuration file section;
  generating pipelines based on declarative rules;
  adding a data deduplication component;
  enabling *join* conditions at the mapping stage.
- **Testing & Validating**: RDF streams have an intrinsic explainability characteristic thanks to annotated entities based on shared data models, which encourages to semantize data as soon as possible in the data processing tool chain.
  It is not clear however, from the data processing architecture, where stream reasoning modules should best stand, both in terms of performance,
  reasoning capabilities, distributed computing, and ease of management. Feedback on architectures and use-cases using the *SMASSIF-RML* tool set should help identify such decision boundaries and design templates (e.g. moving towards full Kappa architectures),
  particularly in terms of ease of implementation/operation and energy efficiency.
  
When contributing, in the general case, please:

* *fork and create merge request* OR
* *raise an issue* into the project's space OR
* improve code based on in-code `TODO` notes or below features list.

See also the `makefile` for development and testing purposes.

Example:
// TODO: get configSection from CLI args
Trigger single pipeline, multiple pipelines from based on CLI or config.ini

## Communication

GitHub issues are the primary way for communicating about specific proposed changes to this project.
Do not open issues for general support questions as we want to keep GitHub issues for bug reports and feature requests.

You may also contact the maintainers by e-mail for more specific purposes and questions.

In both contexts, please be kind and courteous. 
Language issues are often contentious and we'd like to keep discussion brief, civil and focused on what we're actually doing, not wandering off into too much imaginary stuff. 
Likewise any spamming, trolling, flaming, baiting or other attention-stealing behaviour is not welcome.
