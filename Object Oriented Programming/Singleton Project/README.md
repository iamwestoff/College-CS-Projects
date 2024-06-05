## Assignment Description
Programming assignment 4 asks you to create a 'code profiler' using the singleton pattern. This code profiler will be used to measure the duration in time that sections of code take to execute. The profiler class should have a start() method to start a timer and a stop() method to stop the timer. In addition, the profiler will have a count() method to count how many times a section of code has been executed.

Each of these methods will take a string that identifies the timer/counter. start() and stop() will take an optional message as well that is recorded with the duration. So, to measure how long a block of code takes to execute you will do this:

``` C++
//start the profiler 
Profiler.getInstance().start("test timer");

//do something interesting here

//stop the profiler 
Profiler.getInstance().stop("test timer");
```

All pairs of start and stop calls must use the same string to identify an individual timer. The call to start and stop will record the current time. The difference between the two times is the amount of processing time. You should be able to use the same name multiple times to measure a block of code several times. For example, this code:

``` C++
for(int i = 0;i < 100;i++)
{
    Profiler.getInstance().start("test timer", "Iteration: " + i);
 
    //do something interesting here
 
    Profiler.getInstance().stop("test timer" , "Iteration: " + i);
}
```
will record the body of the loop 100 times. Depending on what is happening in the loop the code might take different times to execute. The profiler will record all 100 durations.

Sometimes, you want to know how many times a section of code has executed. The count() method will count the occurrences that this profiler method has been called. So, to count the number of occurrences of code you would do this:

``` C++
Profiler.getInstance().count("inside the foo() method");

```

Occasionally, one might want to turn off profiling while the program is running. Include two methods, setEnabled(boolean) and isEnabled() that control whether the profiler records data.

Next, the profiler should be able to produce a report that lists the average time for each timer name, the longest and shortest time for each timer, and a detailed list of exact timer durations (with the message if there is one), and the counter information. The method should be called generateReport(). The report must be GUI-based and written with care to look clean and professional. You can use HTML in some swing components (https://docs.oracle.com/javase/tutorial/uiswing/components/html).

You should look at the System and HashMap classes.

Things I am looking for:

- apply the singleton pattern to the Profiler class
- have two versions of start() and stop()- one with an optional message one without
- be able to store the duration of time (and perhaps an optional message) for every single call to start and stop
- all calls to start() and stop() with the same identifier are recorded. If calls to start() and stop() are not strictly in order, throw a ProfilerException object
- include a count() method that tracks how many times the passed in id has been executed
- implement the methods to turn on/off profiling 
- professional looking report
  -- use a sophisticated set of widgets (panels, tabs, layouts, etc.)
  -- use html in your widgets (tables, bold, italics, etc.) if that makes your life easier
  -- for each named timer show a summary of: average time, longest time, shortest time
  -- for each named timer show the duration and optional message
  -- counter information
- a driver that tests all of the Profiler functionality

#### Driver File
``` Java
public class Driver
{
    public static void main(String[] args)
    {
        try
        {
            //build up a series of calls to start and stop
            for(int i = 0;i < 100;i++)
            {
                Profiler.getInstance().start("timer1", "start of timer, i: " + i);

                //pause the program for 100 ms
                Thread.sleep(100);

                Profiler.getInstance().stop("timer1", "end of timer, i: " + i);

                //if the counter is an even multiple of 10
                if(i % 10 == 0)
                {
                    //count how many times the counter is an even multiple of 10
                    Profiler.getInstance().count("every 10 count");
                }
            }

            //turn off the profiler
            Profiler.getInstance().setEnabled(false);

            Profiler.getInstance().start("not really a timer");

            Profiler.getInstance().count("every 10 count");
            Profiler.getInstance().count("every 10 count");
            Profiler.getInstance().count("every 10 count");

            Thread.sleep(100);

            Profiler.getInstance().stop("not really a timer");

            //turn the profiler back on
            Profiler.getInstance().setEnabled(true);

            //choose a random amount of time between 0 - 5 sec
            Profiler.getInstance().start("random timer");

            //Math.random() returns a random double between 0.0-1.0 
            Thread.sleep((long)(Math.random() * 5000));

            Profiler.getInstance().stop("random timer");

            //analyze the results of the profiler
            Profiler.getInstance().generateReport();
        }
        catch(ProfilerException ex1)
        {
            ex1.printStackTrace();
        }
        catch(InterruptedException ex2)
        {
            ex2.printStackTrace();
        }
    }
}
```
