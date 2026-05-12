**ID Generation Strategy**
In our microservice architecture, we favor using 64-bit Long values as unique identifiers for **products**. This choice
leverages the efficiency of compact numeric IDs for storage and indexing, and when paired with a distributed ID
generator (such as a Snowflake-like algorithm), it scales well across multiple services without the need for centralized
coordinator.
This approach is in line with the principles described in Designing Data-Intensive Applications (DDIA), which advocates
for balancing performance, scalability and maintainability.

**ID Strategy & Public API Considerations**
Internally, we use 64-bit Long IDs for efficient storage, indexing and query performance in our distributed system.
For external public APIs, exposing raw Long IDs can lead to enumeration risks. To mitigate this:

- UUIDs can be mapped to Longs for external representation.
- Hashing (Hashids) offers a compact, reversible transformation.
- Encryption (AES) ensures full obfuscation if security is critical.


 **API Design**
 - Consistent:  Consistent naming structure convention and structure
 - Stateless: Each API request has all the information needed to process it.
 - Documentation:
     	- Provide clear and comprehensive documentation for each endpoint
            	- Detailing requests and response formats
 	- Possible status codes
 - Error Handling:
     	- Implement standardized error responses to inform clients of issues effectively.


