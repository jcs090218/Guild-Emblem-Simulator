:: ========================================================================
:: $File: build.bat $
:: $Date: 2017-08-18 11:37:58 $
:: $Revision: $
:: $Creator: Jen-Chieh Shen $
:: $Notice: See LICENSE.txt for modification and distribution information
::                    Copyright (c) 2017 by Shen, Jen-Chieh $
:: ========================================================================


set CLASSPATH=.;dist\*;bin\

javac -d bin/ src/com/aldes/jcs/ProgramMain.java
