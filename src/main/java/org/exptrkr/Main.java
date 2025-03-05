package org.exptrkr;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 *
 * @author ronit
 */
public class Main{
public static void main(String[] args) throws IOException, ParseException, SQLException
{
    Repository repo = Repository.getRepository();
    repo.createTables();
    ServiceRun run = new ServiceRun();
    run.showMenu();
    repo.createTables();
}}

