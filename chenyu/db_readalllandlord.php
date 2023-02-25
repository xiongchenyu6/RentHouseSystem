<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
$result = mysql_query("SELECT *FROM `info` WHERE type = 'Landlord'") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["users"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
            $user = array();
            $user["id"] = $row["id"];                
            $user["username"] = $row["username"];

            $user["password"] = $row["password"];

            $user["sex"] = $row["sex"];
            $user["age"] = $row["age"];
            $user["hobby"] = $row["hobby"];
            $user["place"] = $row["place"];
            $user["type"] = $row["type"];
            $user["number"] = $row["number"];
            $user["email"] = $row["email"];
        // push single product into final response array
        array_push($response["users"], $user);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode(($response))
;
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>
