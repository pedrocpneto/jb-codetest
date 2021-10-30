# Juros Baixos codetest

This is a codetest from Juros Baixos which integrates with http://codetest.jurosbaixos.com.br/api-docs/ API.

There will be in this repository two implementations, one Proof-of-concept script in Python located at `python-poc` directory, and a `springboot-java` implementation which, as name implies, is a Web Java implementation with Spring Framework.

## Python proof-of-concept usage
Requires Python 3 and Retry Package (https://pypi.org/project/retry/).

To install Retry;
```shell
$ pip install retry
```

To try getting the treasure:
```shell
$ JB_API_KEY=<your_key> ./poc-python/script.py
```

To reset challenge:
```shell
$ JB_API_KEY=<your_key> ./poc-python/script.py reset
```