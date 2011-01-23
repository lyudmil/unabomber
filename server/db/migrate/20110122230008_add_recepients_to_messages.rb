class AddRecepientsToMessages < ActiveRecord::Migration
  def self.up
    add_column :messages, :recepient_id, :integer
  end

  def self.down
    remove_column :messages, :recepient_id
  end
end
