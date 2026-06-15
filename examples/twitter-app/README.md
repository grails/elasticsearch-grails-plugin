# Twitter example app

This is a simple Elasticsearch example app implementing a basic message website.

You can post short message as different users and tag them for categorization and better searchability.

## Implementation notes

* We use the `sequence` id generator for our domain objects, so that different domain objects do not have colliding ids. That makes our "global search" code easier, where we can find and list hits of different types
