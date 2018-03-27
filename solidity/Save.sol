pragma solidity ^0.4.4;

contract Save {
	string[] sign;

	function setString(string md5) returns(uint,string){
        	var key = sign.push(md5);
     		if(key<0) 
			return(0,'error');
   
        	return (key-1,'susser');
	}
    
    	function getString(uint key) returns(string){
        	return sign[key];
    	}
	
}
