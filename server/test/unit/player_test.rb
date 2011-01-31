require 'test_helper'

class PlayerTest < ActiveSupport::TestCase
  
  test "has a device id" do
    player = Player.new(:device_id => "some-device-id")
    assert player.save
    assert_equal "some-device-id", player.device_id
  end
  
  test "has a current location" do
    player = Player.new(:device_id => "111")
    assert player.save
    
    location = Location.new(:latitude => 9.0, :longitude => 49.0, :altitude => 500)
    assert location.save
    
    player.location = location
    assert player.save
    
    assert_equal player.location, location
  end
  
  test "has a role" do
    player = Player.new(:device_id => "111", :role => :unabomber)
    
    assert player.save
    assert_equal :unabomber, player.role
  end
  
  test "not killed by default" do
    player = Player.new
    
    assert player.save
    assert (not player.reload.killed?)
  end
  
  test "knows if killed" do
    player = Player.new(:killed => true)
    
    assert player.save
    assert player.killed?
  end
  
  test "not arrested by default" do
    player = Player.new
    
    assert player.save
    assert (not player.reload.arrested?)
  end
  
  test "knows if arrested" do
    player = Player.new(:arrested => true)
    
    assert player.save
    assert player.arrested?
  end
  
  test "not active if arrested" do
    player = Player.new(:arrested => true)
    
    assert (not player.active?)
  end
  
  test "not active if killed" do
    player = Player.new(:killed => true)
    
    assert (not player.active?)
  end
  
  test "active if not arrested and not killed" do
    player = Player.new
    
    assert player.active?
  end
  
  test "policeman role assigned every 10 players" do
    player = Player.new(:device_id => "111")
    flexmock(Player).should_receive(:count).and_return(0)
    player.assign_role
    
    assert_equal :policeman, player.role
    
    flexmock(Player).should_receive(:count).and_return(10)
   
    player.assign_role 
    assert_equal :policeman, player.role
  end
  
  test "unabomber role assigned every 10 players" do
    player = Player.new(:device_id => "111")
    flexmock(Player).should_receive(:count).and_return(1)
    
    player.assign_role
    assert_equal :unabomber, player.role
    
    flexmock(Player).should_receive(:count).and_return(11)
    
    player.assign_role
    assert_equal :unabomber, player.role
  end
  
  test "citizen role assigned to all normal players" do
    player = Player.new(:device_id => '111')
    flexmock(Player).should_receive(:count).and_return(2)
    
    player.assign_role
    assert_equal :citizen, player.role
  end
  
  test "assigning a role does not change existing roles" do
    flexmock(Player).should_receive(:count).and_return(3)
    player = Player.new(:device_id => '111', :role => :policeman)
    player.assign_role
    
    assert_equal :policeman, player.role
  end
  
  test "can calculate if it is within range of an explosion" do
    location = Location.new(:longitude => 9.0005, :latitude => 39.0001001, :altitude => 400.4)
    player = Player.new(:device_id => '111', :location => location)
    
    explosion = Location.new(:longitude => 9.0006, :latitude => 39.0000991, :altitude => 400.4)
    
    assert player.within_range_of(explosion)
  end
  
  test "not within range if location unknown" do
    player = Player.new(:device_id => '111', :location => nil)
    explosion = Location.new(:longitude => 9.0006, :latitude => 39.0000991, :altitude => 400.4)
    
    assert (not player.within_range_of(explosion))
  end
  
  test "not within range of a nil location" do
    player = Player.new(:device_id => '111', :location => flexmock(:model, Location))
    
    assert (not player.within_range_of(nil))
  end
  
end
