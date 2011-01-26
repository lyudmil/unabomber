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
    dead_man.should_receive(:killed=).with(true).once
    dead_man.should_receive(:save).once
    
    survivor = flexmock(:model, Player)
    survivor.should_receive(:within_range_of).with(bomb_location).and_return(false)
    survivor.should_receive(:killed=).never
    survivor.should_receive(:save).once
    
    flexmock(Player).should_receive(:all).and_return([dead_man, survivor])
    
    bomb.should_receive(:destroy).once
    
    post :detonate, :id => 33    
  end
  
  test "shows bomb locations in the appropriate format for mixare" do
    bomb1_location = Location.new(:latitude => 44.0, :longitude => 9.0, :altitude => 50.3)
    bomb2_location = Location.new(:latitude => 4.1, :longitude => 39.2, :altitude => 7.6)
    bomb1 = Bomb.new(:location => bomb1_location)
    bomb2 = Bomb.new(:location => bomb2_location)
    flexmock(Bomb).should_receive(:all).and_return([bomb1, bomb2])
    
    get :index
    
    assert_equal [bomb1, bomb2], assigns(:bombs)
    
    json_response = ActiveSupport::JSON.decode(@response.body)
    assert_equal "OK", json_response["status"]
    assert_equal 2, json_response["num_results"]
    
    results = json_response["results"]
    assert_equal 2, results.size
    
    assert_equal 44.0, results[0]["lat"]
    assert_equal 9.0, results[0]["lng"]
    assert_equal 50, results[0]["elevation"]
    assert_equal "Bomb!", results[0]["title"]
    
    assert_equal 4.1, results[1]["lat"]
    assert_equal 39.2, results[1]["lng"]
    assert_equal 7, results[1]["elevation"]
    assert_equal "Bomb!", results[1]["title"]
  end

end
