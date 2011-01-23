class Message < ActiveRecord::Base
  belongs_to :recepient, :class_name => 'Player'
  belongs_to :sender, :class_name => 'Player'
end
