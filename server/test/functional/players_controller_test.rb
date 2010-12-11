require 'test_helper'
require 'flexmock/test_unit'

class PlayersControllerTest < ActionController::TestCase
  
  test "creates a new player" do
    new_player = flexmock(:model, Player)
    new_player.should_receive(:save).once
    flexmock(Player).should_receive(:new).with(:device_id => 'abc123').once.and_return(new_player)
    
    post :create, :device_id => 'abc123'
    
    assert_equal new_player, assigns(:player)
    assert_redirected_to new_player
  end
  
  test "create does nothing if a player with the same device id already exists" do
    existing_player = set_up_player_with_device_id 'abc123'
    flexmock(Player).should_receive(:new).never
    
    post :create, :device_id => 'abc123'
    
    assert_redirected_to existing_player
  end
  
  test "shows a player" do
    player = flexmock(:model, Player, :to_json => nil)
    flexmock(Player).should_receive(:find).and_return(player)
    
    get :show, :id => 3
    
    assert_equal player, assigns(:player)
  end
  
  test "updates player location" do
    put :update, :device_id => 'abc123'
  end
  
  private
  
  def set_up_player_with_device_id device_id
    existing_player = flexmock(:model, Player, :to_json => nil)
    flexmock(Player).should_receive(:find_by_device_id).with(device_id).once.and_return(existing_player)
    existing_player
  end
  
end
