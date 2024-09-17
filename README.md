# Distributed Microservices System

This project represents a distributed microservices system that uses various services and tools for tracing, monitoring, and logging. It integrates with `PostgreSQL` for database management, `Kafka` for messaging, and `OpenTelemetry` for observability. Some services are built with `Spring Boot` and utilize `GraalVM` for enhanced performance in specific services like `Invoice Service`.

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Services](#services)
    - [Order Service](#order-service)
    - [Invoice Service](#invoice-service)
    - [Collector](#collector)
    - [Kafka](#kafka)
- [Tracing and Monitoring Tools](#tracing-and-monitoring-tools)
    - [Zipkin](#zipkin)
    - [Jaeger](#jaeger)
    - [Tempo](#tempo)
    - [Prometheus](#prometheus)
    - [Grafana](#grafana)
- [Installation](#installation)
- [How to Run](#how-to-run)
- [Usage](#usage)
- [Contributing](#contributing)

## Overview

This project is a multi-service distributed system designed for scalability and observability. It uses `OpenTelemetry` for tracing, `Prometheus` for monitoring, and `Grafana` as a visualization tool. It also integrates `Kafka` as a messaging platform and runs services in Docker containers.

## Architecture

The system architecture consists of two core services:
- `Order Service` for managing orders.
- `Invoice Service` for processing invoices (powered by `GraalVM` for optimized runtime).

Additionally, it includes:
- `PostgreSQL` database.
- `Kafka` for message queuing.
- Observability tools like `Zipkin`, `Jaeger`, and `Tempo` for tracing.
- `Prometheus` and `Grafana` for monitoring.

## Services

### Order Service
- **Port**: 8080
- **Technology**: Spring Boot with OpenTelemetry Java Agent
- **Database Schema**: `orders` in `PostgreSQL`
- **Description**: Handles order management, communicates with the database using Spring Data JPA.

### Invoice Service
- **Port**: 8082
- **Technology**: Spring Boot with GraalVM, OpenTelemetry Spring Boot Starter
- **Description**: Manages invoices and uses GraalVM for improved performance and startup time. The service is automatically instrumented with OpenTelemetry for observability.

The **Invoice Service**, using **GraalVM**, is experimentally deployed outside of Docker to explore performance improvements and reduced startup time, whereas the other microservices remain containerized.

### Collector
- **Port**: 4317 (gRPC), 8889 (HTTP)
- **Description**: Collects telemetry data from the services and pushes it to the respective tools like Prometheus, Zipkin, and Jaeger.

### Kafka
- **Port**: 9092 (internal), 29092 (external)
- **Listeners**:
    - `localhost:9092` (internal communication)
    - `localhost:29092` (external communication)
- **Description**: Used for asynchronous message exchange between services.
  Apache Kafka in this project operate without Apache ZooKeeper, it uses KRaft mode introduced in Kafka 2.8.0.
  KRaft (Kafka Raft Metadata Mode) streamlines Kafka deployments and improves operational simplicity by consolidating the components required for managing and running Kafka clusters.

## Tracing and Monitoring Tools

### Zipkin
- **Port**: 9411
- **Description**: Used for distributed tracing. The OpenTelemetry Java agent pushes trace data to Zipkin’s API.

### Jaeger
- **Port**: 16686 (UI), 4317 (API)
- **Description**: Another tracing tool that provides a UI for distributed trace visualization.

### Tempo
- **Port**: 3200 (API), 4317
- **Description**: Supports the OTLP protocol and receives traces from OpenTelemetry agents.

### Prometheus
- **Port**: 9090
- **Description**: Scrapes metrics from the Collector and is used for monitoring.

### Grafana
- **Port**: 3000
- **Description**: Connects to various data sources, including Zipkin, Jaeger, Tempo, Prometheus, and Loki for visualizing monitoring data and distributed traces.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/MikalaiSamtsevich/order-service.git
   cd order-service
   ```

2. Create docker image ./gradlew clean build jibDockerBuild -x test

3. Make sure you have `Docker` and `Docker Compose` installed.

4. Set up your environment variables if needed (e.g., for PostgreSQL, Kafka).

5. Run the Docker Compose setup:
   ```bash
   docker-compose up -d
   ```
6. Now enter invoice-service project
   ```bash
   cd invoice-service
   ```
7. Create compile project with GraalVM
   ```bash
   gradle nativeCompile
   ```
8. Run invoice-service
   ```bash
   cd ./build/native/nativeCompile/invoice-service
   ./invoice-service
   ```

## How to Run

After setting up the Docker containers, you can access the services using the following URLs:

- **Order Service**: `http://localhost:8080`
- **Invoice Service**: `http://localhost:8082`
- **Grafana Dashboard**: `http://localhost:3000` (Default login: `admin/admin`)

## Usage

### Accessing Traces
- **Zipkin**: `http://localhost:9411`
- **Jaeger**: `http://localhost:16686`
- **Tempo**: `http://localhost:3200`
- **Kafka-UI**: `http://localhost:9096`

### Monitoring
- **Prometheus**: `http://localhost:9090`
- **Grafana**: `http://localhost:3000`