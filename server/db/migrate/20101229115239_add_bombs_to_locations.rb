class AddBombsToLocations < ActiveRecord::Migration
  def self.up
		add_column :locations, :bomb_id, :integer
  end

  def self.down
		remove_column :locations, :bomb_id
  end
end
