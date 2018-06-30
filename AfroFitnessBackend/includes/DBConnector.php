<?php
/**
 * Created by PhpStorm.
 * User: Thiga
 * Date: 30-Jun-18
 * Time: 3:08 PM
 */

class DBConnector
{
    private $conn;

    function __construct()
    {
    }

    function connect(){
        include_once dirname(__FILE__).'constants.php';
        $this->conn = new mysqli(DB_HOST,DB_USERNAME,DB_PASSWORD,DB_NAME);

        if(mysqli_connect_errno()){
            echo "Connection Failed: ".mysqli_connect_error();
        }

        return $this->conn;
    }
}