require 'test_helper'
require 'flexmock/test_unit'

class PlayersControllerTest < ActionController::TestCase
  
  test "creates a new player" do
    new_player = flexmock(:model, Player)
    new_player.should_receive(:save).once
    flexmock(Player).should_receive(:new).with('device_id' => 'abc123').once.and_return(new_player)
    
    post :create, :player => { :device_id => 'abc123' }
    
    assert_equal new_player, assigns(:player)
  end
  
  test "updates player location" do
    put :update, :device_id => 'abc123'
  end
  
end