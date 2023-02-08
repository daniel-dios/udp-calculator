# udp-calculator

This is comming from an University Exercise whete my teachers want from me two applications that talk UDP.
There is no any nice way provided by the uni to test this. So I decided to create this repo to show how to do it.

# Requirements

## udpser

A server UDP that answers requests from a given port. Requests are simple operations: sum, subs, mult and div.
Numbers are always from 0,255 and after the operation the server will sum a given secret, then, it will reply to the client with the result.

## udpcli

A client UDP that requests to a given IP and port the operations.
Once it receives the answer it will print the result.
The UDP call must be 10s timeout and, in that case, another message must be printed.



# Solution

Even the message format is open and validations are not mandatory, I decided to model the Domain on each.
You may see code duplicated but since they want do isolated "things" I've decided to split in two different modules.

About the solution, since the messages are simple and there is no mandatory rule to optimize the packages I've decided to send messages like:

- Server request: number from 0 to 255 as plain text (3 bytes as max) + 1 symbor for operation (1 byte) + number from 0 to 255 as plain text (3 bytes as max) 
so the server requests will be 7 bytes.
- Server response: since the longest number it can calculate is 255*255+255 = 65280 (string of 5) so 5 bytes.

## GoldenTest

You will see there how a project emulates what they are gonna test: from two different terminals (in this case, threads) to call each other.
