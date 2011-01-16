require 'test_helper'

class PlayersControllerTest < ActionController::TestCase
  
  test "creates a new player" do
    new_player = flexmock(:model, Player)
    new_player.should_receive(:save).once
    new_player.should_receive(:assign_role).once
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
    player = set_up_player_with_device_id '111'
    flexmock(player).should_receive(:save).once
    
    put :update, :device_id => '111', :longitude => '45.5', :latitude => '8.5', :altitude => '500'
    
    assert_not_nil player.location
    assert_equal BigDecimal.new('45.5'), player.location.longitude
    assert_equal BigDecimal.new('8.5'), player.location.latitude
    assert_equal BigDecimal.new('500'), player.location.altitude
  end
  
  test "destroys previous location of player" do
    player = set_up_player_with_device_id '123'
    previous_location = flexmock(:model, Location)
    flexmock(player).should_receive(:location).and_return(previous_location)
    previous_location.should_receive(:destroy).once
    
    put :update, :device_id => '123'
  end
  
  test "redirects to player after location update" do
    player = set_up_player_with_device_id '123'
    
    put :update, :device_id => '123', :longitude => '45.5', :latitude => '8.5', :altitude => '500'
    
    assert_redirected_to player
  end
  
  test "arrests a player" do
    player = set_up_player_with_device_id '1234'
    
    suspect_location = flexmock(:model, Location)
    suspect = flexmock(:model, Player, :location => suspect_location)
    flexmock(Player).should_receive(:find).with(1).and_return(suspect)

    suspect.should_receive(:destroy).once
    suspect_location.should_receive(:destroy).once
    
    post :arrest, :device_id => '1234', :id => 1
  end
  
  private
  
  def set_up_player_with_device_id device_id
    player = Player.new(:device_id => device_id)
    flexmock(Player).should_receive(:find_by_device_id).with(device_id).once.and_return(player)
    player
  end
  
end
