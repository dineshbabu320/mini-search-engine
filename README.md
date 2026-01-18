# Mini Search Engine

A search engine built with Java and Apache Lucene that indexes web pages and returns relevant URLs based on search queries.

## What It Does

1. Takes a list of URLs as input
2. Fetches and parses HTML content
3. Indexes the text using Apache Lucene
4. Returns relevant URLs ranked by search relevance

## Tech Stack

- Java 21
- Apache Lucene 9.8 (Full-text search)
- JSoup (HTML parsing)
- Maven (Build tool)

## Setup

### Prerequisites
- Java 21+
- Maven 3.9+

### Run
```bash
git clone https://github.com/dineshbabu320/mini-search-engine.git
cd mini-search-engine
mvn compile
mvn exec:java "-Dexec.mainClass=com.dinesh.App"
```

Project Status: 🚧 Work in progress

Author: Dinesh - 3rd Year University Student