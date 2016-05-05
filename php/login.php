<?php
	include_once("connect_database.php");
	$flag=0;
	$phone = "";
	$pass = "";
	if($_POST)
	{
		$phone=$_POST['Phone'];
		$pass=$_POST['Password'];
		$sql="select *, count(phone) as num from userinfo where phone='{$phone}';";
		$data=mysql_query($sql);
		if(!$data)
		{
			echo '0#';
			exit();
		}
		
		$data=mysql_fetch_array($data);
		if($data['num']==0)
		{
			echo '0#';
			exit();
		}
		
		$sql="select password('{$pass}') as encoded";
		$encoded=mysql_fetch_array(mysql_query($sql));
		
		if($encoded['encoded']!=$data['password'])
		{
			echo '1#';
		}
		else
		{
			echo $data['name'] . "$" . $data['phone'] . "$" . $data['bloodgroup'] . "$" . $data['city']."#";
		}
	}
	else
		echo '2#';
?>
