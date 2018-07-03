<?php
/**
 * Created by PhpStorm.
 * User: Thiga
 * Date: 30-Jun-18
 * Time: 4:24 PM
 */
use \Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';
require_once '../includes/user.php';
require_once '../includes/instructor.php';

$app = new \Slim\App([
   'settings' => [
       'displayErrorDetails' => true
   ]
]);

$app->post('/register',function(Request $request,Response $response){
    if(areTheseParametersAvailable(array('first_name','last_name','email','password','gender'))){
        $requestData = $request->getParsedBody();
        $first_name = $requestData['first_name'];
        $last_name = $requestData['last_name'];
        $email = $requestData['email'];
        $password = $requestData['password'];
        $gender = $requestData['gender'];
        $db = new user();
        $responseData = array();

        $result = $db->register($first_name,$last_name,$email,$password,$gender);

        if($result == USER_CREATED){
            $responseData['error'] = false;
            $responseData['message'] = 'Registered Successfully';
            $responseData['user'] = $db->getUser($email);
        }
        elseif ($result == USER_CREATION_FAILED){
            $responseData['error'] = true;
            $responseData['message'] = 'An error occured';
        }
        elseif ($result==USER_EXIST){
            $responseData['error'] = true;
            $responseData['message'] = 'This email is already in use';
        }

        $response->getBody()->write(json_encode($responseData));
    }
});

$app->post('/login',function(Request $request,Response $response){
    if(areTheseParametersAvailable(array('email','password'))){
        $requestData = $request->getParsedBody();
        $email = $requestData['email'];
        $password = $requestData['password'];

        $db = new user();

        $responseData = array();

        if($db->login($email,$password)){
            $responseData['error'] = false;
            $responseData['user'] = $db->getUser($email);
        }
        else{
            $responseData['error'] = true;
            $responseData['message'] = 'Invalid email or password';
        }
        $response->getBody()->write(json_encode($responseData));
    }
});

$app->get('/user/{email}',function (Request $request, Response $response){
    $email = $request->getAttribute('email');
    $db = new user();
    $user = $db->getUserArray($email);
    $response->getBody()->write(json_encode(array("user" => $user)));
});

$app->post('/update/{id}', function (Request $request, Response $response) {
    if(areTheseParametersAvailable(array('first_name','last_name','email','preferred_workout_location','age','gender','weight_kg','target_weight_kg'))){
        $id = $request->getAttribute('id');

        $requestData = $request->getParsedBody();

        $first_name = $requestData['first_name'];
        $last_name = $requestData['last_name'];
        $email = $requestData['email'];
        $preferred_workout_location = $requestData['preferred_workout_location'];
        $age = $requestData['age'];
        $gender = $requestData['gender'];
        $weight = $requestData['weight_kg'];
        $target_weight = $requestData['target_weight_kg'];


        $db = new user();

        $responseData = array();

        if ($db->updateProfile($id, $first_name, $last_name, $email, $preferred_workout_location, $age, $gender, $weight, $target_weight)) {
            $responseData['error'] = false;
            $responseData['message'] = 'Profile Updated Successfully';
            $responseData['user'] = $db->getUser($email);
        } else {
            $responseData['error'] = true;
            $responseData['message'] = 'Update Failed';
        }

        $response->getBody()->write(json_encode($responseData));
    }
});

$app->post('/addsession', function (Request $request, Response $response) {
    if (areTheseParametersAvailable(array('date', 'location_id', 'exercise_type', 'number_of_reps','number_of_sets','user_id'))) {
        $requestData = $request->getParsedBody();
        $date = $requestData['date'];
        $location = $requestData['location_id'];
        $exercise_type = $requestData['exercise_type'];
        $number_of_reps = $requestData['number_of_reps'];
        $number_of_sets = $requestData['number_of_sets'];
        $user = $requestData['user_id'];

        $db = new user();

        $responseData = array();

        if ($db->addSession($date,$location,$exercise_type,$number_of_reps,$number_of_sets,$user)) {
            $responseData['error'] = false;
            $responseData['message'] = 'Workout Session Added Successfully';
        } else {
            $responseData['error'] = true;
            $responseData['message'] = 'Could Not Add Workout Session';
        }

        $response->getBody()->write(json_encode($responseData));
    }
});

$app->get('/sessions/{id}', function (Request $request, Response $response) {
    $user_id = $request->getAttribute('id');
    $db = new user();
    $sessions = $db->getSessions($user_id);
    $response->getBody()->write(json_encode(array("sessions" => $sessions)));
});

$app->get('/locations', function (Request $request, Response $response) {
    $db = new user();
    $locations = $db->getLocations();
    $response->getBody()->write(json_encode(array("locations" => $locations)));
});


$app->get('/instructors', function (Request $request, Response $response) {
    $db = new instructor();
    $instructors = $db->getAllInstructors();
    $response->getBody()->write(json_encode(array("instructors" => $instructors)));
});

function areTheseParametersAvailable($required_fields){
    $error = false;
    $error_fields = "";
    $request_params = $_REQUEST;

    foreach ($required_fields as $field){
        if(!isset($request_params[$field])||strlen(trim($request_params[$field]))<=0){
            $error = true;
            $error_fields.=$field.', ';
        }
    }

    if($error){
        $response = array();
        $response["error"] = true;
        $response["message"] = 'Required fields(s)'.substr($error_fields,0,-2).'is missing or empty';
        echo json_encode($response);
        return false;
    }
    return true;
}
$app->run();