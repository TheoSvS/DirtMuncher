Clone the project locally.

Configure project for Java 21 and run mvn clean install in root directory.

Start the SpringBoot application from DirtMuncherApplication class

Alternatively there is a docker configuration within the project. 
After installing Docker on your machine, open a terminal in the project's root directory and simply run: 

docker compose up --build

Send your POST requests with a tool like Postman at http://localhost:8080/api/v1/execute with content-type application/json


Notes: There are two viable solutions for the dirty patches lookup.

One way is a 2D boolean grid that models the whole
room with the dirty patches having a true value in the grid.

Another way is storing only the dirty patch coordinates, in a Hashmap, where key is the X coordinate of the dirty patches,
and Y coordinate is stored in HashSets that are the values of the HashMap.

Regarding time complexity, both solutions have a constant lookup time O(1). 

However the memory footprint may differ based on the following. 
There is a ratio used to define the relationship of the number of dirty patches to the number of viable positions in the room.
This is called the DirtRatio.

For large rooms this matters for the memory footprint of the application for each request processed.
When the dirt ratio is low (e.g. few dirty patches compared to room size),
the HashMap solution is more memory efficient.

When the dirt ratio is really high , so a lot of the room is dirty, 
the 2D boolean grid has significantly smaller memory footprint, because of the overhead of the HashMap's object creations for most of the room positions.

The application implements both solutions and automatically decides which one to use for each request based on the dirt ratio.

The LookupAlgorithmFootprintStressTest class demonstrates the footprint differences and how the algorithm selects the most efficient one for most of the cases.

Some assertions during the test may fail when the lookup solution selected was not the optimal, but even in these cases the selected strategy is close, with minimal difference.

Absolute accuracy is not possible with this strategy because the distribution of the patches also plays a role apart from the number.
 