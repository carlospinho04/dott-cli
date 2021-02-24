# Dott cli
The purpose of this cli is to check if older products are still being sold in Dott.

## How to run

To run this cli you need build a docker by running thew command `docker build -t dott-cli .` and then you can run the cli with 
`docker run dott-cli filter 2021-01-01T00:00:00 2022-01-01T00:00:00 1-3 4-7 8-12 ">4" "<8"`

Since the data returned from OrderStore is mocked, the api should return: 
````
1-3 months: 0 orders
4-7 months: 1 orders
8-12 months: 2 orders
> 4 months: 3 orders
< 8 months: 1 orders
````

You can also run the test by doing `sbt test`