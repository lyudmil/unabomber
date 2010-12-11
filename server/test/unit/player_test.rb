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
  
end
