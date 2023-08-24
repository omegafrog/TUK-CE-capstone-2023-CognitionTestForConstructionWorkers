using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameCamera : MonoBehaviour
{
    // Start is called before the first frame update

    // Update is called once per frame
    void Update()
    {
        Game1_end();
        Game2_end();
    }

    public void Game1_end() //2라운드로 카메라 이동, 시간 재설정
    {
        if(Move_key.camera1 == true)
        {
            transform.position = new Vector3(-35.568f, 4.071f, 23.972f);
            Move_key.isround2 = true;
            Move_key.camera1 = false;
        }
    }

    public void Game2_end() //3라운드로 카메라이동 , 시간 재설정
    {
        if(Move_key.camera2 == true)
        {
            transform.position = new Vector3(-53.6f, 8.38f, 40.42f);
            Move_key.isround3 = true;
            Move_key.camera2 = false;
        }
    }
}
