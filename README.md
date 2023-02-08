# udp-calculator

This is comming from an University Exercise whete my teachers want from me two applications:

## udpser

A server UDP that answers requests from a given port. Requests are simple operations: sum, subs, mult and div.
Numbers are always from 0,255 and after the operation the server will sum a given secret, then, it will reply to the client with the result.

## udpcli

A client UDP that requests to a given IP and port the operations.
Once it receives the answer it will print the result.
The UDP call must be 10s timeout and, in that case, another message must be printed.


## Solution

Message format is open, validations are not mandatory. There is no any nice way provided by the uni to test this. So I decided to create this repo to show
how to do it.
