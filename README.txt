******************************************************************************************
******************************************************************************************
****                                                                                  ****
****     ███████╗ ██████╗██╗  ██╗███████╗██████╗ ██╗   ██╗██╗     ███████╗██████╗     ****
****     ██╔════╝██╔════╝██║  ██║██╔════╝██╔══██╗██║   ██║██║     ██╔════╝██╔══██╗    ****
****     ███████╗██║     ███████║█████╗  ██║  ██║██║   ██║██║     █████╗  ██████╔╝    ****
****     ╚════██║██║     ██╔══██║██╔══╝  ██║  ██║██║   ██║██║     ██╔══╝  ██╔══██╗    ****
****     ███████║╚██████╗██║  ██║███████╗██████╔╝╚██████╔╝███████╗███████╗██║  ██║    ****
****     ╚══════╝ ╚═════╝╚═╝  ╚═╝╚══════╝╚═════╝  ╚═════╝ ╚══════╝╚══════╝╚═╝  ╚═╝    ****
****                                                             Scheduler v1.0.0     ****
****     AUTHOR: Courtney McEntire <cmcent6@wgu.com>                                  ****
****     RELEASED: 2022-12-09                                                         ****
****     VERSION: 1.0.0-rc                                                            ****
****                                                                                  ****
****     BUILT WITH: > IntelliJ IDEA Ultimate Edition 2022.3                          ****
****                 > OpenJDK 17.0.5                                                 ****
****                 > JavaFX 17.0.2                                                  ****
****                 > mysql-connector-java 8.0.282                                   ****
 ****                                                                                ****
  **************************************************************************************
  **************************************************************************************
 ****                                                                                ****
****     DESCRIPTION: A tool used to manage appointments with a set of customers.     ****
****       The user can schedule, edit, and cancel appointments,  as well as add,     ****
****       edit, and delete customers.   Various reports can be generated to gain     ****
****       insights into customer relationships and appointment data.                 ****
****                                                                                  ****
****       Data is persisted in a MySQL database and appointment dates are stored     ****
****       in UTC time. On log in, all appointment times will be displayed in the     ****
****       system's configured time zone.                                             ****
****                                                                                  ****
****       Currently the tool has limited French localization (Log In form).          ****
 ****                                                                                ****
  **************************************************************************************
  **************************************************************************************
 ****                                                                                ****
****     HOW TO USE: On launch the user will be greeted with a log in form. Demo      ****
****       users can log in with username "test" and password "test". Upon login,     ****
****       the user will load into the Appointments view, with all scheduled          ****
****       appointments viewed by default. The user can adjust to see only appoint-   ****
****       ments for the next week, the current month, or all by selecting the        ****
****       relevant radio button in the top right. The user can add an appointment    ****
****       by clicking "Add" in the top right which will display a pane used to       ****
****       collect the relevant data. If an appointment is selected in the table,     ****
****       the edit and delete options in the top right will be enabled.              ****
****                                                                                  ****
****       The user can navigate between Appointment, Customer, and Report views      ****
****       via the buttons in the top-left. Managing customers is similar to manag-   ****
****       ing appointments.                                                          ****
****                                                                                  ****
****       In the Reports view, the user can select a report option via buttons in    ****
****       upper-left region which will generate the report in the text field,        ****
****       allowing the user the copy/export the data.                                ****
 ****                                                                                ****
  **************************************************************************************
  **************************************************************************************
 ****                                                                                ****
****     REPORT:  Included is a custom report  (third option)  that displays the      ****
****       total number of hours scheduled for each customer, total number of inst-   ****
****       ances, and the average length of each meeting.                             ****
****                                                                                  ****
******************************************************************************************
******************************************************************************************

Additional notes:
 - Lambda method usage can be searched with the comment tag "LAMBDA_USAGE".
    - Used most frequently to describe callback functions used to determine the text
    to display in a TableView Column/Cell.
 - Functional Interface usage can be searched with the comment tag "FUNCTIONAL_INTERFACE"