require 'test_helper'

class MessageTest < ActiveSupport::TestCase
  
  test "has a recepient" do
    player = Player.create(:device_id => '111')
    message = Message.new(:recepient => player)
    
    assert message.save
    assert_equal player, message.reload.recepient
    assert player.messages.include?(message)
  end
  
  test "has a sender" do
    sender = Player.create(:device_id => '222')
    message = Message.new(:sender => sender)
    
    assert message.save
    assert_equal sender, message.reload.sender
    assert sender.sent_messages.include?(message)
  end
  
  test "has content" do
    message = Message.new(:content => 'Hello, there!')
    
    assert message.save
    assert_equal 'Hello, there!', message.reload.content
  end
  
end
