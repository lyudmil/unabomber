class AddPlayerIdToBombs < ActiveRecord::Migration
  def self.up
		add_column :bombs, :player_id, :integer
  end

  def self.down
		remove_column :bombs, :player_id
  end
end
