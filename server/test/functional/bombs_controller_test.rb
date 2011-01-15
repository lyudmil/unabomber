require 'test_helper'

class BombsControllerTest < ActionController::TestCase
  
  test "can place a bomb" do
		bomb = flexmock(:model, Bomb, :to_json => nil)
		player = flexmock(:model, Player)
		location = flexmock(:model, Location)

		flexmock(Player).should_receive(:find_by_device_id).with('111').and_return(player).once
		flexmock(Location).should_receive(:create).with(:latitude => 4.0, :longitude => 5.0, :altitude => 6.0).and_return(location).once
		flexmock(Bomb).should_receive(:new).with(:player => player, :location => location).and_return(bomb)

		bomb.should_receive(:save).once

		post :place, :device_id => '111', :latitude => 4.0, :longitude => 5.0, :altitude => 6.0
  end

end
