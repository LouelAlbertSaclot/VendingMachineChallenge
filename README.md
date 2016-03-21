#Simple Vending Application
This was the coding challenge that Orbitz (Chicago) had given me during my application. They had liked the code but during the final interview, they said they were looking for a more senior developer (probably more Spring/Hibernate experience). The Vending application I created is purely using java core. No other frameworks were used except for JUNIT and Swing (UI) .

Here are some of the assumptions/expectations from the Application:
===========================================================================================
 * The application can take as many products but the UI only will use 4.
 * Input from the UI is only with the denomination [$1, $0.25, $0.10, and $0.05].
 * Coin Return only happens when customer clicks the 'Coin Return' button.
 * Loading of configuration file was done with minimal error handling as this was just an add-on for a dynamic product load.
 * Application can perform price increase/decrease to individual products but UI only has price updates for all.
 * No stock limit is inplace but has an initial count of 4 items per product available for dispense.
 * No Tax calculation was included as might result to an output that is not a multiple of $0.05
 * No concurrency code added to run a system check.
 * No real persistance and email notification.
 * No real logging (log4j) included as it would just be a SysOut to nothing.


Some Design Patterns Used: 
===========================================================================================
 * Command
 * Observer
 * Delegate
 * Factory
 * MVC (or at least something close to it)
 
Notes to consider: 
===========================================================================================
 * Facade Pattern is not properly implemented in this case as project is too simple to really show the use of the said pattern.
 * The JUNIT codes could have been made better.