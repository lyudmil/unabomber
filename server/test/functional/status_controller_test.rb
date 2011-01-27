require 'test_helper'

class StatusControllerTest < ActionController::TestCase
  
  def setup
    @player = flexmock(:model, Player, :active? => true, :killed? => false, :arrested? => false)
    flexmock(Player).should_receive(:find_by_device_id).with('333').and_return(@player)
  end
  
  test "game just started if fewer than three players" do
    flexmock(Player).should_receive(:count).and_return(2)
    
    get :index, :device_id => '333'
    
    assert_equal 'started', @response.body
  end
  
  test "can tell if a player has been killed" do
    player = flexmock(:model, Player, :killed? => true, :arrested? => false)
    flexmock(Player).should_receive(:find_by_device_id).with('111').and_return(player)
    flexmock(Player).should_receive(:count).and_return(3)
    
    get :index, :device_id => '111'
    
    assert_equal 'finished-killed', @response.body
  end
  
  test "can tell if a player has been arrested" do
    player = flexmock(:model, Player, :arrested? => true, :killed? => false)
    flexmock(Player).should_receive(:find_by_device_id).with('222').and_return(player)
    flexmock(Player).should_receive(:count).and_return(3)
    
    get :index, :device_id => '222'
    
    assert_equal 'finished-jail', @response.body
  end
  
  test "can tell if a citizen has won" do
    @player.should_receive(:role).and_return('citizen')
    flexmock(Player).should_receive(:where).with(:role => 'citizen').and_return([@player])
    flexmock(Player).should_receive(:count).and_return(3)
    
    no_unabombers
    
    get :index, :device_id => '333'
    
    assert_equal 'finished-win', @response.body    
  end
  
  test "can tell if a cop has won" do
    @player.should_receive(:role).and_return('policeman')
    flexmock(Player).should_receive(:where).with(:role => 'citizen').and_return([flexmock(:model, Player, :active? => true)])
    flexmock(Player).should_receive(:count).and_return(3)
    no_unabombers
    
    get :index, :device_id => '333'
    
    assert_equal 'finished-win', @response.body
  end
  
  test "can tell if a cop has lost" do
    @player.should_receive(:role).and_return('policeman')
    no_citizens
    flexmock(Player).should_receive(:where).with(:role => 'unabomber').and_return([flexmock(:model, Player, :active? => true)])
    flexmock(Player).should_receive(:count).and_return(3)
    
    get :index, :device_id => '333'
    
    assert_equal 'finished-lose', @response.body
  end
  
  test "can tell if a unabomber has won" do
    flexmock(Player).should_receive(:count).and_return(3)
    @player.should_receive(:role).and_return('unabomber')
    no_citizens
    
    get :index, :device_id => '333'
    
    assert_equal 'finished-win', @response.body
  end
  
  test "can tell if a game is still in progress" do
    @player.should_receive(:killed?).and_return(false)
    @player.should_receive(:role).and_return(:citizen)
    flexmock(Player).should_receive(:where).with(:role => 'citizen').and_return([@player, flexmock(:model, Player, :active? => true)])
    flexmock(Player).should_receive(:where).with(:role => 'unabomber').and_return([flexmock(:model, Player, :active? => true)])
    flexmock(Player).should_receive(:count).and_return(3)
    
    get :index, :device_id => '333'
    
    assert_equal 'started', @response.body
  end
  
  private
  
  def no_unabombers
    arrested_unabomber = flexmock(:model, Player, :role => 'unabomber', :active? => false)
    flexmock(Player).should_receive(:where).with(:role => 'unabomber').and_return([arrested_unabomber, arrested_unabomber])
  end
  
  def no_citizens
    dead_citizen = flexmock(:model, Player, :role => 'citizen', :active? => false)
    flexmock(Player).should_receive(:where).with(:role => 'citizen').and_return([dead_citizen, dead_citizen])
  end
  
end
