<?php
/**
 * Created by PhpStorm.
 * User: Thiga
 * Date: 30-Jun-18
 * Time: 3:17 PM
 */

class user
{
    private $conn;

    function __construct()
    {
        require_once dirname(__FILE__).'/DBConnector.php';
        $db = new DBConnector();
        $this->conn = $db->connect();
    }

    function register($first_name,$last_name,$email,$password,$gender){
        if(!$this->doesUserExist($email)){
            $pass = password_hash($password,PASSWORD_DEFAULT);
            $stmt = $this->conn->prepare("INSERT INTO users_094781(first_name,last_name,email,password,gender)VALUES(?,?,?,?,?)");
            $stmt->bind_param("sssss",$first_name,$last_name,$email,$pass,$gender);
            if ($stmt->execute())
				return USER_CREATED;
			return USER_CREATION_FAILED;
        }
        return USER_EXIST;
    }

    function login($email, $pass)
    {
		$res = mysqli_query($this->conn,"SELECT * FROM users_094781") or die("Error: ".mysqli_error($this->conn));

        while ($row=mysqli_fetch_array($res)){
            if(password_verify($pass,$row['password']) && $email==$row['email']){
				$stmt = $this->conn->prepare("SELECT id FROM users_094781 WHERE email = ? AND password = ?");
				$stmt->bind_param("ss", $email, $row['password']);
				$stmt->execute();
				$stmt->store_result();
				return $stmt->num_rows > 0;
            }
        }
    }
	
	function updateProfile($id, $first_name, $last_name, $email, $preferred_workout_location, $age, $gender, $weight, $target_weight)
    {
        $stmt = $this->conn->prepare("UPDATE users_094781 SET first_name = ?,last_name = ?, email = ?, preferred_workout_location = ?, age = ?,gender = ?, weight_kg = ?,target_weight_kg = ? WHERE id = ?");
        $stmt->bind_param("ssssisiii", $first_name,$last_name, $email, $preferred_workout_location, $age, $gender, $weight,$target_weight, $id);
        if ($stmt->execute())
            return true;
        return false;
    }
	
	    function addSession($date,$location,$exercise_type,$number_of_reps,$number_of_sets,$user){
        $stmt = $this->conn->prepare("INSERT INTO sessions_094781(date,location_id,exercise_type,number_of_reps,number_of_sets,user_id)VALUES(?,?,?,?,?,?)");
        $stmt->bind_param("sisiii",$date,$location,$exercise_type,$number_of_reps,$number_of_sets,$user);
        if ($stmt->execute())
            return true;
        return false;
    }

    function getSessions($user_id){
        $stmt = $this->conn->prepare("SELECT sessions_094781.id,sessions_094781.date,sessions_094781.location_id,sessions_094781.exercise_type,sessions_094781.number_of_reps,sessions_094781.number_of_sets,gym_locations_094781.location_name FROM sessions_094781 INNER JOIN gym_locations_094781 ON sessions_094781.location_id=gym_locations_094781.id WHERE sessions_094781.user_id = ?;");
        $stmt->bind_param("i", $user_id);
        $stmt->execute();
        $stmt->bind_result($id,$date,$location,$exercise_type,$number_of_reps,$number_of_sets,$location_name);

        $sessions = array();

        while ($stmt->fetch()) {
            $temp = array();

            $temp['id'] = $id;
            $temp['date'] = $date;
            $temp['location'] = $location;
            $temp['exercise_type'] = $exercise_type;
            $temp['number_of_reps'] = $number_of_reps;
            $temp['number_of_sets'] = $number_of_sets;
			$temp['location_name'] = $location_name;

            array_push($sessions, $temp);
        }

        return $sessions;
    }

    function getLocations(){
        $stmt = $this->conn->prepare("SELECT * FROM gym_locations_094781;");
        $stmt->execute();
        $stmt->bind_result($id,$location_name,$opening_time,$closing_time,$latitude,$longitude);

        $locations = array();

        while ($stmt->fetch()) {
            $temp = array();

            $temp['id'] = $id;
            $temp['location_name'] = $location_name;
            $temp['opening_time'] = $opening_time;
            $temp['closing_time'] = $closing_time;
            $temp['latitude'] = $latitude;
            $temp['longitude'] = $longitude;

            array_push($locations, $temp);
        }

        return $locations;
    }


    function getUser($email){
        $stmt = $this->conn->prepare("SELECT id,first_name,last_name,email,preferred_workout_location,age,gender,weight_kg,target_weight_kg FROM users_094781 WHERE email = ?");
        $stmt->bind_param("s",$email);
        $stmt->execute();
        $stmt->bind_result($id,$first_name,$last_name,$email,$preferred_workout_location, $age, $gender, $weight, $target_weight);
        $stmt->fetch();
        $user = array();
        $user['id'] = $id;
        $user['first_name'] = $first_name;
        $user['last_name'] = $last_name;
        $user['email'] = $email;
        $user['preferred_workout_location'] = $preferred_workout_location;
        $user['age'] = $age;
        $user['gender'] = $gender;
        $user['weight_kg'] = $weight;
        $user['target_weight_kg'] = $target_weight;
        return $user;
    }
	
		function getUserArray($email){
        $stmt = $this->conn->prepare("SELECT id,first_name,last_name,email,preferred_workout_location,age,gender,weight_kg,target_weight_kg FROM users_094781 WHERE email = ?");
        $stmt->bind_param("s",$email);
        $stmt->execute();
        $stmt->bind_result($id,$first_name,$last_name,$email,$preferred_workout_location, $age, $gender, $weight, $target_weight);
        $stmt->fetch();
        $user = array();
		$temp = array();
        $temp['id'] = $id;
        $temp['first_name'] = $first_name;
        $temp['last_name'] = $last_name;
        $temp['email'] = $email;
        $temp['preferred_workout_location'] = $preferred_workout_location;
        $temp['age'] = $age;
        $temp['gender'] = $gender;
        $temp['weight_kg'] = $weight;
        $temp['target_weight_kg'] = $target_weight;
		array_push($user,$temp);
        return $user;
    }

    function doesUserExist($email){
        $stmt = $this->conn->prepare("SELECT id FROM users_094781 WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
        return $stmt->num_rows > 0;
    }
}