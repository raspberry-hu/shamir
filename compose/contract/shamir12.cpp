#include <eosio/eosio.hpp>
#include <eosio/print.hpp>

using namespace eosio;
using namespace std;

class [[eosio::contract("shamir12")]] shamir12 : public eosio::contract {
    public:

        // 构造函数
        shamir12(name receiver, name code, datastream<const char*> ds): contract(receiver, code, ds) {}

        [[eosio::action]]
        void create(name account, string owner, string shamirkey, string shamiruserkey, string category, string saving_time) {
             // 插入一条数据

            require_auth(account);
            records_index recordset(get_first_receiver(), get_first_receiver().value);

            uint64_t currentId = recordset.available_primary_key();
            recordset.emplace(account, [&](auto& row) {
                row.id = currentId;
                row.owner = owner;
                row.shamirkey = shamirkey;
              	row.shamiruserkey = shamiruserkey;
                row.category = category;
                row.saving_time = saving_time;
                row.count = 0;
            });
            print("the current id is ", currentId);
        }

        [[eosio::action]]
        void get(uint64_t id) {
            // 获取对应的ipfs_hash

            records_index recordset(get_self(), get_first_receiver().value);

            auto iterator = recordset.find(id);
            check(iterator != recordset.end(), "the record does not exist");

            //找到，则相应的访问次数加一
            recordset.modify(iterator, get_self(), [&](auto& row) {
                row.count++;
	    });

            print("The ", id, "'s shamirhash is ", iterator->shamirkey);
            print("\n The count is ", iterator->count);
        }

        [[eosio::action]]
        void update(name account, uint64_t id, string owner, string category, string saving_time) {
            // 更新字段值

            require_auth(account);
            records_index recordset(get_first_receiver(), get_first_receiver().value);

            auto iterator = recordset.find(id);
            check(iterator != recordset.end(), "the record does not exist");

            recordset.modify(iterator, account, [&](auto& row) {
                row.owner = owner;
                row.category = category;
                row.saving_time = saving_time;
            });

            print("update successfully");
        }

        [[eosio::action]]
        void erase(name account, uint64_t id) {
            // 删除一条数据

            require_auth(account);
            records_index recordset(get_first_receiver(), get_first_receiver().value);
            auto iterator = recordset.find(id);
            check(iterator != recordset.end(), "the record does not exist");

            recordset.erase(iterator);
            print("erase successfully");
        }

        [[eosio::action]]
        void getuserkey(name account, string shamiruserkey) {
            require_auth(account);
            records_index recordset(get_self(), get_first_receiver().value);
            auto iterator = recordset.begin();
            while(iterator != recordset.end()) {
                if(iterator->shamiruserkey == shamiruserkey) {
                    print("The ", iterator->id, "shamirhash is ", iterator->shamirkey);
                    iterator++;
                }else{
                    iterator++;
                }
            }
        }

        [[eosio::action]]
        void eraseuserkey(name account, string shamiruserkey) {
            require_auth(account);
            records_index recordset(get_self(), get_first_receiver().value);
            auto iterator = recordset.begin();
            while(iterator != recordset.end()) {
                if(iterator->shamiruserkey == shamiruserkey) {
                    iterator = recordset.erase(iterator);
                    print("erase successfully");
                    // return;
                }else{
                    iterator++;
                }
            }
        }
    private:
        struct [[eosio::table]] records
        {
            uint64_t id;        // 数据id，主键
            string owner;       // shamir数据拥有者
            string shamirkey;   // 上传shamirKey的hash
          	string shamiruserkey; //shamir的唯一标识
            string category;    // 数据
            string saving_time; // 存储在数据库的时间
            uint64_t count;     // 链上访问次数

            uint64_t primary_key() const { return id; }
            uint64_t get_secondary_1() const { return count; }
        };

        using records_index = eosio::multi_index<"metaset"_n, records, indexed_by<"bycount"_n,
        const_mem_fun<records, uint64_t, &records::get_secondary_1>>>;

};