class AddRolesToPlayers < ActiveRecord::Migration
  def self.up
    add_column :players, :role, :string
  end

  def self.down
    remove_column :players, :role
  end
end
