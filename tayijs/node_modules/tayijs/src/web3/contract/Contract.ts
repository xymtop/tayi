export class Contract{

    //部署合约
    public  deploy = (): string  => {

        return  "hi"
    };

    //执行有参合约
    public execute = (args:string[]) :string => {
        return  "hi"
    }


    //执行无参合约
    public  call = () :string => {
        return  "hi"
    }

}