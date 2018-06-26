pragma solidity ^0.4.21;
  
import "./StandardBurnableToken.sol";

contract ECPoints is StandardBurnableToken {
  string public name = "ECPoints";
  string public symbol = "ECP";
  uint8 public decimals = 0;
  uint256 public INITIAL_SUPPLY = 10000000000000000;

  address public owner;

  function ECPoints() public {
    totalSupply_ = INITIAL_SUPPLY;
    balances[msg.sender] = INITIAL_SUPPLY;
    owner = msg.sender;
  }
}
