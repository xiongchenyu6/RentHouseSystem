<?php


foreach ($_REQUEST as $key => $value)
{
	if ($key == "pageValue") {
		$pageValue = $value/100;
	}
}


// EDIT: Your mysql database account information
$username = "root";
$password = "";
$database = "renthousesystem";
$tablename = "status";
$localhost = "localhost";


// Check Connection to Database
if (mysql_connect($localhost, $username, $password))
  {
  	@mysql_select_db($database) or die ("Unable to select database");


    // Next two lines will write into your table 'test_table_name_here' with 'yourdata' value from the arduino and will timestamp that data using 'now()'
    $query = "INSERT INTO $tablename(pageValue) VALUES ($pageValue)";
  	$result = mysql_query($query);
echo('able to connect to database.');
  } else {
  	echo('Unable to connect to database.');
  }


?>
