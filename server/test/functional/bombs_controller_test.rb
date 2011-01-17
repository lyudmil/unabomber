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
  
  test "can detonate a bomb" do
    bomb_location = flexmock(:model, Location)
    bomb = flexmock(:model, Bomb, :id => 33, :location => bomb_location)
    flexmock(Bomb).should_receive(:find).with(33).and_return(bomb)
    
    dead_man_location = flexmock(:model, Location)
    dead_man = flexmock(:model, Player, :location => dead_man_location)
    dead_man.should_receive(:within_range_of).with(bomb_location).and_return(true)
    dead_man.should_receive(:destroy).once
    dead_man_location.should_receive(:destroy).once
    
    survivor = flexmock(:model, Player)
    survivor.should_receive(:within_range_of).with(bomb_location).and_return(false)
    survivor.should_receive(:destroy).never
    
    flexmock(Player).should_receive(:all).and_return([dead_man, survivor])
    
    post :detonate, :id => 33    
  end

end
