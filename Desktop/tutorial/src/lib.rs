use scrypto::prelude::*;

#[derive(NonFungibleData)]

struct EventTicket {
    number:u64
}

blueprint! {
    struct GumballMachine {

        collected_xrd: Vault,
        votes_char : HashMap<String, Decimal>,
        last_update: u64,
        allow_vote_char:bool,
        allow_vote_mod:bool,
        token_admin: Vault,
        component_admin_resource_address: ResourceAddress,
        ticket_nft_address: ResourceAddress,
        id:u64,
    }

    impl GumballMachine {

        pub fn instantiate_machine() -> (ComponentAddress, Bucket) {
            
            let component_admin: Bucket = ResourceBuilder::new_fungible()
                .metadata("name", "System Admin")
                .divisibility(DIVISIBILITY_NONE)
                .initial_supply(1);

            let token_admin: Bucket = ResourceBuilder::new_fungible()
                .divisibility(DIVISIBILITY_NONE)
                .initial_supply(1);

            let ticket_nft: ResourceAddress = ResourceBuilder::new_non_fungible()
                .metadata("name", "Event Ticket")
                .updateable_non_fungible_data(rule!(require(component_admin.resource_address())), LOCKED)
                .restrict_withdraw(rule!(deny_all), MUTABLE(rule!(require(component_admin.resource_address()))))
                .no_initial_supply();

            let mut component = Self {
                collected_xrd: Vault::new(RADIX_TOKEN),
                votes_char: HashMap::new(),
                last_update: Runtime::current_epoch(),
                allow_vote_char: false,
                allow_vote_mod: false,
                token_admin: Vault::with_bucket(token_admin),
                component_admin_resource_address: component_admin.resource_address(),
                ticket_nft_address: ticket_nft,
                id : 1,
            }
            .instantiate();

            let comp = component_admin.resource_address();

            let access_rules: AccessRules = AccessRules::new()
                .method("show_votes", rule!(require(comp)))
                .method("let_vote_char", rule!(require(comp)))
                .default(rule!(allow_all));

            component.add_access_check(access_rules);
            

            (component.globalize(), component_admin)
        }

        pub fn take_ticket(&mut self, payment: Bucket)->(Bucket, Bucket) {
            let mut ticket_bucket = Bucket::new(self.ticket_nft_address);
            let ticket_resource_manager = borrow_resource_manager!(ticket_bucket.resource_address());

            let eventTicket = EventTicket{
                number:self.id,
            };
            let ticket_id = &NonFungibleId::from_u64(self.id);
            self.id = self.id + 1;
            ticket_bucket.put(ticket_resource_manager.mint_non_fungible(ticket_id, eventTicket));

          
            (ticket_bucket, payment)
        }
        pub fn vote_char(&mut self, mut payment: Bucket, name_in_vote: String) -> Bucket {

            if self.allow_vote_char {
                let num = payment.amount();
                self.collected_xrd.put(payment.take(num));

                if self.votes_char.contains_key(&name_in_vote){
                    info!("Name already present : {name_in_vote}");

                    for(name, vote_number) in self.votes_char.iter_mut() {
                        if name.eq(&name_in_vote) {
                            let buff = *vote_number + num;
                            *vote_number = buff;
                            info!("Added votes, now: {name}, {buff}");
                        }
                    }
                }
                else {
                    self.votes_char.insert(name_in_vote.to_string(),num);

                    info!("New candidate added : {name_in_vote}, {num}");
                }

                payment
            }
            else {
                info!("Vote is closed, try later, allow_vote_char: {}", self.allow_vote_char);

                payment
            }

        }

        pub fn take_share(&mut self) -> Bucket {
            self.collected_xrd.take_all()
            
        }
        pub fn give_xrd(&mut self,payment: Bucket){
            self.collected_xrd.put(payment);

        ()
  
        }

        pub fn show_votes(&self){
            for (key, val) in &self.votes_char {
                info!("key: {key} val: {val}");
            }
            ()
        }
        pub fn show_xrd(&mut self) {
            
            info!("XRD: {{self.collected_xrd.to_string}}");

            ()

        }

        pub fn show_time(&mut self) {
            ()

        }

        pub fn decide_winner_char(&mut self){
            let mut buff_name:String = "Default".to_string();
            let mut buff_val: Decimal = Decimal::from(0); 

            for (key, val) in &self.votes_char {
                if val > &buff_val {
                    buff_name = key.to_string();
                    buff_val = *val;
                }
            }
            info!("buff_name: {}",buff_name);
            info!("buff_val: {}",buff_val);


        }
        
        pub fn let_vote_char(&mut self) {
            self.allow_vote_char = true;

            info!("You are allowed to vote for charity now, allow_vote_char is now: {}", self.allow_vote_char)

        }
        pub fn let_vote_mod(&mut self) {
            self.allow_vote_mod = true;

            info!("You are allowed to vote for moderator now, allow_vote_char is now: {}", self.allow_vote_mod)

        }
        pub fn end_vote_char(&mut self){
            self.allow_vote_char = false;

            info!("You are NOT allowed to vote for charity now, allow_vote_char is now: {}", self.allow_vote_char)
        }
        pub fn end_vote_mod(&mut self){
            self.allow_vote_mod =false;

            info!("You are NOT allowed to vote for moderator now, allow_vote_char is now: {}", self.allow_vote_mod)
            
        }
        
    }
    
}


