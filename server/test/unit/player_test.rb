require 'test_helper'

class PlayerTest < ActiveSupport::TestCase
  test "has a device id" do
    player = Player.new(:device_id => "some-device-id")
    assert player.save
    assert_equal "some-device-id", player.device_id
  end
end
